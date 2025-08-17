package ru.site.web.model;

import java.util.UUID;

import ru.site.datasource.enums.GameStatus;

public class GameResponse {
    private UUID uuid;
    private String message;
    private int[][] field;
    private GameStatus gameStatus;
    private Long currentPlayerId;

    public GameResponse() {}

    public UUID getId() { return uuid; }

    public void setId(UUID uuid) { this.uuid = uuid; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public int[][] getField() { return field; }

    public void setField(int[][] field) { this.field = field; }

    public GameStatus getGameStatus() { return gameStatus; }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Long getCurrentPlayerId() { return currentPlayerId; }

    public void setCurrentPlayerId(Long currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
}
