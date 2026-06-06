package com.yjt.basketball.dto.row;

import java.time.LocalDate;

/**
 * 球员单场比赛得分走势（用于折线图）
 */
public record PlayerGamePointRow(
        Integer gameId,
        LocalDate gameDate,
        Integer points,
        Integer rebounds,
        Integer assists,
        Integer steals,
        Integer blocks
) {
}
