package ru.site.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import ru.site.datasource.enums.GameStatus;
import ru.site.datasource.enums.GameType;

public class Game {

    private Field field = Field.createEmptyField();
    private UUID uuid;
    private GameStatus status = GameStatus.WAITING;
    private GameType gameType = GameType.HVS_C;
    private String message = "";
    private Long playerXId;
    private Long playerOId;
    private Long currentPlayerId;
    private Long winnerId = null;
    private String playerSign;
    private Integer lastMoveRow;
    private Integer lastMoveCol;
    private LocalDateTime eventDateTime;

    public Game() {
        // empty
    }

    public LocalDateTime getEventDateTime() { return eventDateTime; }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public void setCell(int row, int col, int value) {
        int[][] matrix = field.getField();
        matrix[row][col] = value;
    }

    public Integer getLastMoveRow() { return lastMoveRow; }

    public void setLastMoveRow(Integer lastMoveRow) {
        this.lastMoveRow = lastMoveRow;
    }

    public Integer getLastMoveCol() { return lastMoveCol; }

    public void setLastMoveCol(Integer lastMoveCol) {
        this.lastMoveCol = lastMoveCol;
    }

    public String getPlayerSign() { return playerSign; }

    public void setPlayerSign(String playerSign) {
        this.playerSign = playerSign;
    }

    public Field getField() { return field; }

    public void setField(Field field) { this.field = field; }

    public UUID getUuid() { return uuid; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }

    public GameType getGameType() { return gameType; }

    public void setGameType(GameType gameType) {
        if ("HvsC".equals(gameType)) {
            this.setOtherPlayerId(0L);
        }
        this.gameType = gameType;
    }

    public GameStatus getStatus() { return status; }

    public void setStatus(GameStatus status) { this.status = status; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public int getCurrentPlayerValue() {
        if (currentPlayerId == null) {
            throw new IllegalStateException("Current player id is null");
        }
        if (currentPlayerId.equals(playerXId)) {
            return 1;
        } else if (currentPlayerId.equals(playerOId)) {
            return -1;
        } else {
            throw new IllegalStateException(
                "Current player id doesn't match any player");
        }
    }

    public void setPlayerBySign(String sign, Long playerId) {
        if ("X".equalsIgnoreCase(sign)) {
            this.playerXId = playerId;
            if (this.currentPlayerId == null)
                this.currentPlayerId = playerId;
        } else if ("O".equalsIgnoreCase(sign)) {
            this.playerOId = playerId;
            if (this.currentPlayerId == null)
                this.currentPlayerId = playerId;
        } else {
            throw new IllegalArgumentException("Unknown player sign: " + sign);
        }
    }

    public void setOtherPlayerId(Long playerId) {
        if (this.playerXId == null) {
            this.playerXId = playerId;
            this.status = GameStatus.IN_PROGRESS;
        } else if (this.playerOId == null) {
            this.playerOId = playerId;
            this.status = GameStatus.IN_PROGRESS;
        } else {
            throw new IllegalStateException("Both player IDs are already set");
        }
    }

    public Long getOpponentPlayerId() {
        if (currentPlayerId == null) {
            return null;
        }
        if (currentPlayerId.equals(playerXId)) {
            return playerOId;
        } else if (currentPlayerId.equals(playerOId)) {
            return playerXId;
        } else {
            return null;
        }
    }

    public Long getPlayerXId() { return playerXId; }

    public void setPlayerXId(Long playerXId) { this.playerXId = playerXId; }

    public Long getPlayerOId() { return playerOId; }

    public void setPlayerOId(Long playerOId) { this.playerOId = playerOId; }

    public Long getCurrentPlayerId() { return currentPlayerId; }

    public void setCurrentPlayerId(Long currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public Long getWinnerId() { return winnerId; }

    public void setWinnerId(Long winnerId) { this.winnerId = winnerId; }
}
