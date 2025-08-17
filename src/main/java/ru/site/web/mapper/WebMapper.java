package ru.site.web.mapper;

import ru.site.domain.model.Game;
import ru.site.web.model.GameWeb;

public class WebMapper {

    public static void toDomain(GameWeb gameWeb, Game game) {
        if (gameWeb == null || game == null) {
            throw new IllegalArgumentException(
                "gameWeb and game must not be null");
        }
        game.setLastMoveRow(gameWeb.getRow());
        game.setLastMoveCol(gameWeb.getCol());
    }
}
