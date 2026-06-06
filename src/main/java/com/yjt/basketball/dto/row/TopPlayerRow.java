package com.yjt.basketball.dto.row;

import java.math.BigDecimal;

public class TopPlayerRow {

    private Integer playerId;
    private String playerName;
    private String teamName;
    private BigDecimal avgPoints;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public BigDecimal getAvgPoints() {
        return avgPoints;
    }

    public void setAvgPoints(BigDecimal avgPoints) {
        this.avgPoints = avgPoints;
    }
}
