package com.yjt.basketball.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RetiredPlayer")
public class RetiredPlayer {

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "retired_year")
    private Integer retiredYear;

    @Column(name = "hall_of_fame")
    private Boolean hallOfFame;

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

    public Integer getRetiredYear() {
        return retiredYear;
    }

    public void setRetiredYear(Integer retiredYear) {
        this.retiredYear = retiredYear;
    }

    public Boolean getHallOfFame() {
        return hallOfFame;
    }

    public void setHallOfFame(Boolean hallOfFame) {
        this.hallOfFame = hallOfFame;
    }
}
