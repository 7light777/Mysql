package com.yjt.basketball.controller;

import com.yjt.basketball.common.ApiResponse;
import com.yjt.basketball.dto.CurrentStatsDTO;
import com.yjt.basketball.dto.PlayerGameDetailDTO;
import com.yjt.basketball.dto.UpdateStatsRequest;
import com.yjt.basketball.service.PageDataService;
import com.yjt.basketball.service.StatsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;
    private final PageDataService pageDataService;

    public StatsController(StatsService statsService, PageDataService pageDataService) {
        this.statsService = statsService;
        this.pageDataService = pageDataService;
    }

    @GetMapping("/details")
    public ApiResponse<List<PlayerGameDetailDTO>> findDetails(@RequestParam(required = false) String playerName,
                                                              @RequestParam(required = false) Integer gameId,
                                                              @RequestParam(required = false) String teamName,
                                                              @RequestParam(required = false) java.time.LocalDate startDate,
                                                              @RequestParam(required = false) java.time.LocalDate endDate) {
        return ApiResponse.success(statsService.findDetails(playerName, gameId, teamName, startDate, endDate));
    }

    @PutMapping
    public ApiResponse<Map<String, Object>> updateStats(@Valid @RequestBody UpdateStatsRequest request) {
        statsService.updateStats(request);
        return ApiResponse.success("球员比赛技术统计更新成功", Map.of(
                "playerId", request.playerId(),
                "gameId", request.gameId()
        ));
    }

    @GetMapping("/current")
    public ApiResponse<CurrentStatsDTO> getCurrentStats(@RequestParam Integer playerId,
                                                        @RequestParam Integer gameId) {
        return ApiResponse.success(pageDataService.getCurrentStats(playerId, gameId));
    }
}
