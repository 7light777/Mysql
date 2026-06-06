package com.yjt.basketball.controller;

import com.yjt.basketball.common.ApiResponse;
import com.yjt.basketball.dto.DeleteGameResultDTO;
import com.yjt.basketball.dto.GameDetailDTO;
import com.yjt.basketball.dto.GameListItemDTO;
import com.yjt.basketball.dto.GamePlayerStatsDTO;
import com.yjt.basketball.service.GameService;
import com.yjt.basketball.service.PageDataService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final PageDataService pageDataService;

    public GameController(GameService gameService, PageDataService pageDataService) {
        this.gameService = gameService;
        this.pageDataService = pageDataService;
    }

    @GetMapping
    public ApiResponse<List<GameListItemDTO>> findGames(@RequestParam(required = false) Integer gameId,
                                                        @RequestParam(required = false) String teamName,
                                                        @RequestParam(required = false) LocalDate startDate,
                                                        @RequestParam(required = false) LocalDate endDate) {
        return ApiResponse.success(pageDataService.findGames(gameId, teamName, startDate, endDate));
    }

    @GetMapping("/{gameId}")
    public ApiResponse<GameDetailDTO> getGameDetail(@PathVariable Integer gameId) {
        return ApiResponse.success(pageDataService.getGameDetail(gameId));
    }

    @GetMapping("/{gameId}/players")
    public ApiResponse<List<GamePlayerStatsDTO>> getGamePlayers(@PathVariable Integer gameId) {
        return ApiResponse.success(pageDataService.findGamePlayers(gameId));
    }

    @DeleteMapping("/{gameId}")
    public ApiResponse<DeleteGameResultDTO> deleteGame(@PathVariable Integer gameId) {
        return ApiResponse.success("比赛及比赛表现记录删除成功", gameService.deleteGameWithStats(gameId));
    }
}
