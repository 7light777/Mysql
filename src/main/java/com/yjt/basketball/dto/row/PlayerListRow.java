package com.yjt.basketball.dto.row;

import java.math.BigDecimal;

public class PlayerListRow {

    private Integer playerId;
    private String name;
    private String position;
    private Integer currentTeamId;
    private String teamName;
    private String status;
    private Long gamesPlayed;
    private BigDecimal avgPoints;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getCurrentTeamId() {
        return currentTeamId;
    }

    public void setCurrentTeamId(Integer currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public BigDecimal getAvgPoints() {
        return avgPoints;
    }

    public void setAvgPoints(BigDecimal avgPoints) {
        this.avgPoints = avgPoints;
    }
}
