package ru.site.domain.service;
import java.util.List;
import java.util.UUID;

import ru.site.datasource.model.Leader;
import ru.site.domain.model.Game;

public interface GameService {

    void getStepHvsC(Game game);

    void getStepHvsH(Game game, Long currentPlayerId);

    boolean validField(int[][] oldGame, int[][] newGame);

    int endGame(Game game);

    List<Game> getGameList(Long userUuid);

    Game getLastGame(Long userUuid);

    List<Game> getCompleted(Long userUuid);

    void saveGame(Game game);

    Game getGame(UUID uuid);

    List<Leader> getLeaders(Integer n);
}