package com.yjt.basketball.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayerGameStatsId implements Serializable {

    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "game_id")
    private Integer gameId;

    public PlayerGameStatsId() {
    }

    public PlayerGameStatsId(Integer playerId, Integer gameId) {
        this.playerId = playerId;
        this.gameId = gameId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerGameStatsId that)) {
            return false;
        }
        return Objects.equals(playerId, that.playerId) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, gameId);
    }
}
