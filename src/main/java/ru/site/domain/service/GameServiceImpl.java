package ru.site.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import ru.site.datasource.enums.GameStatus;
import ru.site.datasource.mapper.SourceMapper;
import ru.site.datasource.model.GameStore;
import ru.site.datasource.model.Leader;
import ru.site.datasource.service.GameRepositoryService;
import ru.site.datasource.service.LeadersRepositoryService;
import ru.site.datasource.service.UserRepositoryService;
import ru.site.domain.model.Game;

public class GameServiceImpl implements GameService {

    public final UserRepositoryService userRepositoryService;
    public final GameRepositoryService gameRepositoryService;
    public final LeadersRepositoryService leadersRepositoryService;
    public final SourceMapper sourceMapper;

    public GameServiceImpl(UserRepositoryService userRepositoryService,
                           GameRepositoryService gameRepositoryService,
                           LeadersRepositoryService leadersRepositoryService,
                           SourceMapper sourceMapper) {

        this.userRepositoryService = userRepositoryService;
        this.gameRepositoryService = gameRepositoryService;
        this.leadersRepositoryService = leadersRepositoryService;
        this.sourceMapper = sourceMapper;
    }

    private String endGameProcess(int result, Game game) {

        switch (result) {
        case 1 -> game.setWinnerId(game.getCurrentPlayerId());
        case -1 -> game.setWinnerId(game.getOpponentPlayerId());
        case 0 -> game.setWinnerId(null);
        default -> {
        }
        }
        switch (result) {
        case 1 -> game.setStatus(GameStatus.WINNING);
        case -1 -> game.setStatus(GameStatus.WINNING);
        case 0 -> game.setStatus(GameStatus.DRAW);
        default -> {
        }
        }
        GameStore gameS = sourceMapper.toStorage(game);
        gameRepositoryService.saveGame(gameS);

        return switch (result) {
            case 1 -> "Выиграл игрок " + game.getCurrentPlayerId();
            case -1 -> "Выиграл игрок " + game.getOpponentPlayerId();
            case 0 -> "Ничья (комп лучше) ";
            default -> "Неизвестный результат игры\n";
        };
    }

    @Override
    public void getStepHvsC(Game game) {

        int[][] oldGame = getGame(game.getUuid()).getField().getField();

        oldGame = Arrays.stream(oldGame)
                      .map(row -> Arrays.copyOf(row, row.length))
                      .toArray(int[][] ::new);

        game.setCell(game.getLastMoveRow(), game.getLastMoveCol(),
                     game.getCurrentPlayerValue());

        int[][] newGame = game.getField().getField();

        if (!validField(oldGame, newGame)) {
            game.setMessage("Неправильноe изменение матрицы");
            return;
        }

        int result = endGame(game);

        if (result < 2) {
            game.setMessage(endGameProcess(result, game));
            return;
        }

        int bestScore = Integer.MIN_VALUE;
        int jIndex = 0;
        int iIndex = 0;
        int currentPlayer = -game.getCurrentPlayerValue();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getField().getField()[i][j] == 0) {
                    game.getField().getField()[i][j] = currentPlayer;
                    int score = minmax(game, -currentPlayer);
                    game.getField().getField()[i][j] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        jIndex = j;
                        iIndex = i;
                    }
                }
            }
        }

        game.getField().getField()[iIndex][jIndex] = currentPlayer;

        result = endGame(game);

        if (result < 2) {
            game.setMessage(endGameProcess(result, game));
            return;
        }

        GameStore newGame2 = sourceMapper.toStorage(game);
        gameRepositoryService.saveGame(newGame2);
    }

    @Override
    public void getStepHvsH(Game game, Long currentPlayerId) {

        if (!game.getCurrentPlayerId().equals(currentPlayerId)) {
            game.setMessage("Не твой ход");
            return;
        }

        game.setCell(game.getLastMoveRow(), game.getLastMoveCol(),
                     game.getCurrentPlayerValue());

        int result = endGame(game);

        if (result < 2) {
            game.setMessage(endGameProcess(result, game));
            return;
        }
        game.setMessage("успех успешный");

        game.setCurrentPlayerId(game.getOpponentPlayerId());
        GameStore newGame2 = sourceMapper.toStorage(game);
        gameRepositoryService.saveGame(newGame2);
    }

    @Override
    public boolean validField(int[][] oldGame, int[][] newGame) {

        int changes = 0;
        int size = oldGame.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < oldGame[i].length; j++) {
                if (oldGame[i][j] != newGame[i][j]) {
                    changes++;

                    if (oldGame[i][j] != 0) {
                        return false;
                    }

                    int newValue = newGame[i][j];
                    if (newValue != 1 && newValue != -1) {
                        return false;
                    }
                }
            }
        }

        return changes == 1;
    }

    @Override
    public int endGame(Game game) {

        int[][] f = game.getField().getField();

        for (int i = 0; i < 3; i++) {
            int sumRow = f[i][0] + f[i][1] + f[i][2];
            if (sumRow == 3 || sumRow == -3)
                return sumRow / 3;

            int sumCol = f[0][i] + f[1][i] + f[2][i];
            if (sumCol == 3 || sumCol == -3)
                return sumCol / 3;
        }

        int sumMainDiag = f[0][0] + f[1][1] + f[2][2];
        if (sumMainDiag == 3 || sumMainDiag == -3)
            return sumMainDiag / 3;

        int sumAntiDiag = f[0][2] + f[1][1] + f[2][0];
        if (sumAntiDiag == 3 || sumAntiDiag == -3)
            return sumAntiDiag / 3;

        boolean hasEmpty = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (f[i][j] == 0) {
                    hasEmpty = true;
                    break;
                }
            }
            if (hasEmpty)
                break;
        }
        if (!hasEmpty)
            return 0;
        return 2;
    }

    public int minmax(Game game, int currentPlayer) {
        int winner = endGame(game);
        int bestScore = 0;
        if (winner < 2) {
            return winner * 10;
        }

        if (currentPlayer == 1) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (game.getField().getField()[i][j] == 0) {
                        game.getField().getField()[i][j] = currentPlayer;
                        int score = minmax(game, -currentPlayer);
                        game.getField().getField()[i][j] = 0;
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (game.getField().getField()[i][j] == 0) {
                        game.getField().getField()[i][j] = currentPlayer;
                        int score = minmax(game, -currentPlayer);
                        game.getField().getField()[i][j] = 0;
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }
        return bestScore;
    }

    public String matrixToString(int[][] field) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                sb.append(field[i][j]);
                if (j < field[i].length - 1) {
                    sb.append(" ");
                }
            }
            if (i < field.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public List<Game> getGameList(Long userUuid) {
        List<GameStore> games = gameRepositoryService.findByPlayerXId(userUuid);
        List<GameStore> games2 =
            gameRepositoryService.findByPlayerOId(userUuid);
        List<GameStore> allGames = new ArrayList<>();
        allGames.addAll(games);
        allGames.addAll(games2);
        List<Game> list = new ArrayList<>();
        for (GameStore game : allGames) {
            list.add(sourceMapper.toDomain(game));
        }
        return list;
    }

    public Game getLastGame(Long userUuid) {
        List<Game> allGames = getGameList(userUuid);
        Game curGame = null;
        for (Game game : allGames) {
            if (GameStatus.IN_PROGRESS.equals(game.getStatus())) {
                curGame = game;
            }
        }
        return curGame;
    }
    public List<Game> getCompleted(Long userUuid) {
        List<Game> allGames = getGameList(userUuid);
        List<Game> completed = new ArrayList<>();
        for (Game game : allGames) {
            if (GameStatus.DRAW.equals(game.getStatus()) ||
                GameStatus.WINNING.equals(game.getStatus())) {
                completed.add(game);
            }
        }
        return completed;
    }

    public void saveGame(Game game) {
        GameStore gameS =
            gameRepositoryService.saveGame(sourceMapper.toStorage(game));
        game.setUuid(gameS.getUuid());
    }
    public Game getGame(UUID uuid) {
        return sourceMapper.toDomain(gameRepositoryService.getGame(uuid));
    }

    public List<Leader> getLeaders(Integer n) {
        Integer count = 0;
        List<Leader> users = new ArrayList<>();
        List<Leader> leaders = leadersRepositoryService.getLeaders(n);
        for (Leader leader : leaders) {
            if (count <= n) {
                users.add(leader);
            } else {
                break;
            }
        }
        return users;
    }
}
