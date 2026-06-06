package com.yjt.basketball.controller;

import com.yjt.basketball.common.ApiResponse;
import com.yjt.basketball.dto.DashboardDTO;
import com.yjt.basketball.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ApiResponse<DashboardDTO> getDashboard() {
        return ApiResponse.success(dashboardService.getDashboard());
    }
}
