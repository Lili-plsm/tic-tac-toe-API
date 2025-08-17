package ru.site.web.mapper;

import ru.site.domain.model.Game;
import ru.site.web.model.GameResponse;

public class GameResponseMapper {

    public GameResponse toResponse(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("game must not be null");
        }

        GameResponse gameResponse = new GameResponse();
        gameResponse.setId(game.getUuid());
        gameResponse.setMessage(game.getMessage());
        gameResponse.setCurrentPlayerId(game.getCurrentPlayerId());
        gameResponse.setGameStatus(game.getStatus());
        gameResponse.setField(game.getField().getField());

        return gameResponse;
    }
}
