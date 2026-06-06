package com.yjt.basketball.dto.row;

import java.time.LocalDate;

public class ScoutNoteRow {

    private Integer noteId;
    private Integer playerId;
    private String playerName;
    private String teamName;
    private String content;
    private Integer potentialRating;
    private LocalDate noteDate;

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPotentialRating() {
        return potentialRating;
    }

    public void setPotentialRating(Integer potentialRating) {
        this.potentialRating = potentialRating;
    }

    public LocalDate getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(LocalDate noteDate) {
        this.noteDate = noteDate;
    }
}
