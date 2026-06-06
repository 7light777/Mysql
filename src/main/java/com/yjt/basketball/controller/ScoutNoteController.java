package com.yjt.basketball.controller;

import com.yjt.basketball.common.ApiResponse;
import com.yjt.basketball.dto.CreateScoutNoteRequest;
import com.yjt.basketball.dto.RecentScoutNoteDTO;
import com.yjt.basketball.dto.UpdateScoutNoteRequest;
import com.yjt.basketball.entity.ScoutNote;
import com.yjt.basketball.service.PageDataService;
import com.yjt.basketball.service.ScoutNoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/scout-notes")
public class ScoutNoteController {

    private final ScoutNoteService scoutNoteService;
    private final PageDataService pageDataService;

    public ScoutNoteController(ScoutNoteService scoutNoteService, PageDataService pageDataService) {
        this.scoutNoteService = scoutNoteService;
        this.pageDataService = pageDataService;
    }

    @GetMapping("/recent")
    public ApiResponse<java.util.List<RecentScoutNoteDTO>> findRecentScoutNotes() {
        return ApiResponse.success(pageDataService.findRecentScoutNotes());
    }

    @GetMapping
    public ApiResponse<java.util.List<RecentScoutNoteDTO>> findScoutNotes(@RequestParam(required = false) String playerName,
                                                                          @RequestParam(required = false) Integer minRating,
                                                                          @RequestParam(required = false) Integer maxRating,
                                                                          @RequestParam(required = false) String keyword,
                                                                          @RequestParam(required = false) Integer page,
                                                                          @RequestParam(required = false) Integer size) {
        return ApiResponse.success(pageDataService.findScoutNotes(playerName, minRating, maxRating, keyword, page, size));
    }

    @PostMapping
    public ApiResponse<ScoutNote> createScoutNote(@Valid @RequestBody CreateScoutNoteRequest request) {
        return ApiResponse.success("球探笔记添加成功", scoutNoteService.createScoutNote(request));
    }

    @PutMapping("/{noteId}")
    public ApiResponse<ScoutNote> updateScoutNote(@PathVariable Integer noteId,
                                                  @RequestBody UpdateScoutNoteRequest request) {
        return ApiResponse.success("球探笔记修改成功", scoutNoteService.updateScoutNote(noteId, request));
    }

    @DeleteMapping("/{noteId}")
    public ApiResponse<Map<String, Object>> deleteScoutNote(@PathVariable Integer noteId) {
        scoutNoteService.deleteScoutNote(noteId);
        return ApiResponse.success("球探笔记删除成功", Map.of("noteId", noteId));
    }
}
