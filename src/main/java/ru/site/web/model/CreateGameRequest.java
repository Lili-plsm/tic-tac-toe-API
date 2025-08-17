package ru.site.web.model;

public class CreateGameRequest {

    private String gameType;
    private String playerSign;

    public CreateGameRequest() {}

    public CreateGameRequest(String gameType, String playerSign) {
        this.gameType = gameType;
        this.playerSign = playerSign;
    }

    public String getGameType() { return gameType; }

    public void setGameType(String gameType) { this.gameType = gameType; }

    public String getPlayerSign() { return playerSign; }

    public void setPlayerSign(String playerSign) {
        this.playerSign = playerSign;
    }
}
