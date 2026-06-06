package com.yjt.basketball.controller;

import com.yjt.basketball.common.ApiResponse;
import com.yjt.basketball.dto.PlayerListItemDTO;
import com.yjt.basketball.dto.PlayerProfileDTO;
import com.yjt.basketball.service.PageDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PageDataService pageDataService;

    public PlayerController(PageDataService pageDataService) {
        this.pageDataService = pageDataService;
    }

    @GetMapping
    public ApiResponse<List<PlayerListItemDTO>> findPlayers(@RequestParam(required = false) String playerName,
                                                            @RequestParam(required = false) Integer teamId,
                                                            @RequestParam(required = false) String position,
                                                            @RequestParam(required = false) String status) {
        return ApiResponse.success(pageDataService.findPlayers(playerName, teamId, position, status));
    }

    @GetMapping("/{playerId}/profile")
    public ApiResponse<PlayerProfileDTO> getPlayerProfile(@PathVariable Integer playerId) {
        return ApiResponse.success(pageDataService.getPlayerProfile(playerId));
    }
}
