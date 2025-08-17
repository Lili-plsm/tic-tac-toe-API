package ru.site.datasource.mapper;

import ru.site.datasource.model.FieldStore;
import ru.site.datasource.model.GameStore;
import ru.site.domain.model.Field;
import ru.site.domain.model.Game;

public class SourceMapper {

    public GameStore toStorage(Game game) {
        GameStore gameStore = new GameStore();
        gameStore.setUuid(game.getUuid());
        gameStore.setField(mapToStorage(game.getField()));
        gameStore.setGameType(game.getGameType());
        gameStore.setStatus(game.getStatus());
        gameStore.setPlayerXId(game.getPlayerXId());
        gameStore.setPlayerOId(game.getPlayerOId());
        gameStore.setCurrentPlayerId(game.getCurrentPlayerId());
        gameStore.setWinnerId(game.getWinnerId());
        return gameStore;
    }

    public Game toDomain(GameStore gameStore) {
        Game game = new Game();
        game.setUuid(gameStore.getUuid());
        game.setField(mapToDomain(gameStore.getField()));
        game.setGameType(gameStore.getGameType());
        game.setStatus(gameStore.getStatus());
        game.setPlayerXId(gameStore.getPlayerXId());
        game.setPlayerOId(gameStore.getPlayerOId());
        game.setCurrentPlayerId(gameStore.getCurrentPlayerId());
        game.setWinnerId(gameStore.getWinnerId());
        return game;
    }

    private FieldStore mapToStorage(Field field) {
        FieldStore fieldStore = new FieldStore();
        fieldStore.setField(field.getField());
        return fieldStore;
    }

    private Field mapToDomain(FieldStore fieldStore) {
        Field field = new Field();
        field.setField(fieldStore.getField());
        return field;
    }
}
