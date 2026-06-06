package com.yjt.basketball.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ActivePlayer")
public class ActivePlayer {

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "current_team_id")
    private Integer currentTeamId;

    @Column(name = "jersey_number")
    private Integer jerseyNumber;

    @Column(name = "years_exp")
    private Integer yearsExp;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getCurrentTeamId() {
        return currentTeamId;
    }

    public void setCurrentTeamId(Integer currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public Integer getYearsExp() {
        return yearsExp;
    }

    public void setYearsExp(Integer yearsExp) {
        this.yearsExp = yearsExp;
    }
}
