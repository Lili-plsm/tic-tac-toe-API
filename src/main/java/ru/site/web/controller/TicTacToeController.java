package ru.site.web.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.site.datasource.enums.GameType;
import ru.site.datasource.model.User;
import ru.site.domain.model.Game;
import ru.site.domain.service.GameService;
import ru.site.domain.service.UserService;
import ru.site.web.mapper.GameResponseMapper;
import ru.site.web.model.CreateGameRequest;
import ru.site.web.model.GameResponse;
import ru.site.web.model.GameWeb;


@RestController
public class TicTacToeController {

    private final GameService gameService;
    private final UserService userService;
    private final GameResponseMapper responseMapper;

    public TicTacToeController(GameService gameService,
                               UserService userService,
                               GameResponseMapper responseMapper) {
        this.gameService = gameService;
        this.userService = userService;
        this.responseMapper = responseMapper;
    }

    @PostMapping("/game/{uuid}")
    public ResponseEntity<GameResponse> playMove(@PathVariable UUID uuid,
                                                 @RequestBody GameWeb gameWeb) {

        String login = userService.getCurrentLogin();
        Long currentPlayerId = userService.getUserIdByLogin(login);

        Game game = gameService.getGame(uuid);
        game.setLastMoveRow(gameWeb.getRow());
        game.setLastMoveCol(gameWeb.getCol());
        if ("HvsC".equals(game.getGameType()))
            gameService.getStepHvsC(game);
        else
            gameService.getStepHvsH(game, currentPlayerId);

        GameResponse gameResponse = responseMapper.toResponse(game);
        return ResponseEntity.ok(gameResponse);
    }

    @PostMapping("/init")
    public ResponseEntity<GameResponse>
    initGame(@Valid @RequestBody CreateGameRequest createGame) {

        Long creatorPlayerId =
            userService.getUserIdByLogin(userService.getCurrentLogin());
        Game game = new Game();

        game.setPlayerBySign(createGame.getPlayerSign(), creatorPlayerId);
        if (createGame.getGameType().equals(GameType.HVS_H.name())) {
            game.setGameType(GameType.HVS_H);
        } else {
            game.setGameType(GameType.HVS_C);
            game.setOtherPlayerId(0L);
        }

        gameService.saveGame(game);

        GameResponse gameResponse = responseMapper.toResponse(game);

        return ResponseEntity.status(HttpStatus.CREATED).body(gameResponse);
    }

    @GetMapping("/add/{gameId}")
    public ResponseEntity<?> gameAdd(@PathVariable UUID gameId) {
        Long playerId =
            userService.getUserIdByLogin(userService.getCurrentLogin());
        Game game = gameService.getGame(gameId);
        game.setOtherPlayerId(playerId);
        gameService.saveGame(game);

        return ResponseEntity.ok(Map.of("message", "Игрок добавлен в игру"));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Game>> gameList() {
        Long playerId =
            userService.getUserIdByLogin(userService.getCurrentLogin());
        return ResponseEntity.ok(gameService.getGameList(playerId));
    }

    @GetMapping("/info")
    public ResponseEntity<?> userInfo() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(
            Map.of("id", user.getId(), "login", user.getLogin()));
    }

    @GetMapping("/current_game")
    public ResponseEntity<?> currentGame() {
        Long playerId =
            userService.getUserIdByLogin(userService.getCurrentLogin());
        Game game = gameService.getLastGame(playerId);
        if (game == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Доступные игры не найдены"));
        else
            return ResponseEntity.ok(game);
    }
    @GetMapping("/completed/{playerId}")
    public ResponseEntity<?> completed(@PathVariable Long playerId) {
        List<Game> games = gameService.getCompleted(playerId);
        return ResponseEntity.ok(games);
    }
}
