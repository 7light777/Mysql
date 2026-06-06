package com.yjt.basketball.controller.web;

import com.yjt.basketball.service.PageDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class PageController {

    private final PageDataService pageDataService;

    public PageController(PageDataService pageDataService) {
        this.pageDataService = pageDataService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "首页 / 仪表盘");
        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("dashboardPage", pageDataService.getDashboardPage());
        return "index";
    }

    @GetMapping("/players")
    public String players(@RequestParam(required = false) String playerName,
                          @RequestParam(required = false) Integer teamId,
                          @RequestParam(required = false) String position,
                          @RequestParam(required = false) String status,
                          Model model) {
        model.addAttribute("pageTitle", "球员档案");
        model.addAttribute("activeMenu", "players");
        model.addAttribute("players", pageDataService.findPlayers(playerName, teamId, position, status));
        model.addAttribute("teams", pageDataService.findTeamOptions());
        model.addAttribute("positions", pageDataService.findPositionOptions());
        model.addAttribute("playerName", playerName);
        model.addAttribute("teamId", teamId);
        model.addAttribute("position", position);
        model.addAttribute("status", status);
        return "players";
    }

    @GetMapping("/players/{playerId}")
    public String playerDetail(@PathVariable Integer playerId, Model model) {
        model.addAttribute("pageTitle", "球员详情");
        model.addAttribute("activeMenu", "players");
        model.addAttribute("playerId", playerId);
        model.addAttribute("profile", pageDataService.getPlayerProfile(playerId));
        return "player-detail";
    }

    @GetMapping("/games")
    public String games(@RequestParam(required = false) Integer gameId,
                        @RequestParam(required = false) String teamName,
                        @RequestParam(required = false) LocalDate startDate,
                        @RequestParam(required = false) LocalDate endDate,
                        Model model) {
        model.addAttribute("pageTitle", "比赛管理");
        model.addAttribute("activeMenu", "games");
        model.addAttribute("games", pageDataService.findGames(gameId, teamName, startDate, endDate));
        model.addAttribute("gameId", gameId);
        model.addAttribute("teamName", teamName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "games";
    }

    @GetMapping("/games/{gameId}")
    public String gameDetail(@PathVariable Integer gameId, Model model) {
        model.addAttribute("pageTitle", "比赛详情");
        model.addAttribute("activeMenu", "games");
        model.addAttribute("game", pageDataService.getGameDetail(gameId));
        return "game-detail";
    }

    @GetMapping("/scout-notes")
    public String scoutNotes(Model model) {
        model.addAttribute("pageTitle", "球探报告");
        model.addAttribute("activeMenu", "scoutNotes");
        model.addAttribute("players", pageDataService.findPlayers(null, null));
        model.addAttribute("notes", pageDataService.findScoutNotes(null, null, null, null, 0, 20));
        return "scout-notes";
    }

    @GetMapping("/stats-query")
    public String statsQuery(Model model) {
        model.addAttribute("pageTitle", "数据分析");
        model.addAttribute("activeMenu", "statsQuery");
        return "stats-query";
    }

    @GetMapping("/stats-update")
    public String statsUpdate(Model model) {
        model.addAttribute("pageTitle", "更新比赛数据");
        model.addAttribute("activeMenu", "statsUpdate");
        model.addAttribute("games", pageDataService.findGames());
        return "stats-update";
    }

    @GetMapping("/delete-game")
    public String deleteGame() {
        return "redirect:/games";
    }
}
