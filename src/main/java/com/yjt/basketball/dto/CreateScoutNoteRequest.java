package com.yjt.basketball.dto;

import jakarta.validation.constraints.NotNull;

public record CreateScoutNoteRequest(
        @NotNull(message = "不能为空") Integer playerId,
        String content,
        Integer potentialRating
) {
}
