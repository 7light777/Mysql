package com.yjt.basketball.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateStatsRequest(
        @NotNull(message = "不能为空") Integer playerId,
        @NotNull(message = "不能为空") Integer gameId,
        @NotNull(message = "不能为空") Integer points,
        @NotNull(message = "不能为空") Integer rebounds,
        @NotNull(message = "不能为空") Integer assists,
        @NotNull(message = "不能为空") Integer steals,
        @NotNull(message = "不能为空") Integer blocks
) {
}
