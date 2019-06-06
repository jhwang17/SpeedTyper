package com.example.speedtyper;

public class Result {

    private int resultID;
    private String playerInitials;
    private int playerScore;

    public Result() {
        resultID = -1;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public String getPlayerInitials() {
        return playerInitials;
    }

    public void setPlayerInitials(String playerInitials) {
        this.playerInitials = playerInitials;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
