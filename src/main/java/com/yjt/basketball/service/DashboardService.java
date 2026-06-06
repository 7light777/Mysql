package com.yjt.basketball.service;

import com.yjt.basketball.dto.DashboardDTO;
import com.yjt.basketball.dto.row.TopPlayerRow;
import com.yjt.basketball.mapper.DashboardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    public DashboardService(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }

    public DashboardDTO getDashboard() {
        List<DashboardDTO.TopPlayerDTO> topPlayers = dashboardMapper.selectTopPlayersForPage(5)
                .stream()
                .map(this::toTopPlayerDTO)
                .toList();

        return new DashboardDTO(
                dashboardMapper.countTeams(),
                dashboardMapper.countPlayers(),
                dashboardMapper.countGames(),
                dashboardMapper.countStats(),
                topPlayers
        );
    }

    private DashboardDTO.TopPlayerDTO toTopPlayerDTO(TopPlayerRow row) {
        return new DashboardDTO.TopPlayerDTO(
                row.getPlayerId(),
                row.getPlayerName(),
                row.getAvgPoints()
        );
    }
}
