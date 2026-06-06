package com.yjt.basketball.service;

import com.yjt.basketball.dto.CreateScoutNoteRequest;
import com.yjt.basketball.dto.UpdateScoutNoteRequest;
import com.yjt.basketball.entity.ScoutNote;
import com.yjt.basketball.mapper.ScoutNoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScoutNoteService {

    private static final Logger log = LoggerFactory.getLogger(ScoutNoteService.class);

    private final ScoutNoteMapper scoutNoteMapper;

    public ScoutNoteService(ScoutNoteMapper scoutNoteMapper) {
        this.scoutNoteMapper = scoutNoteMapper;
    }

    public ScoutNote createScoutNote(CreateScoutNoteRequest request) {
        log.info("===== 新增球探笔记 =====");
        log.info("新增参数：playerId={}, potentialRating={}, content={}",
                request.playerId(), request.potentialRating(),
                request.content() != null ? request.content().substring(0, Math.min(30, request.content().length())) + "..." : "null");
        ScoutNote scoutNote = new ScoutNote();
        scoutNote.setPlayerId(request.playerId());
        scoutNote.setContent(request.content());
        scoutNote.setPotentialRating(request.potentialRating());
        scoutNote.setNoteDate(LocalDate.now());
        scoutNoteMapper.insertScoutNote(scoutNote);
        log.info("新增球探笔记成功：noteId={}", scoutNote.getNoteId());
        return scoutNote;
    }

    public ScoutNote updateScoutNote(Integer noteId, UpdateScoutNoteRequest request) {
        log.info("===== 修改球探笔记 =====");
        log.info("修改参数：noteId={}, potentialRating={}, noteDate={}",
                noteId, request.potentialRating(), request.noteDate());
        ScoutNote scoutNote = scoutNoteMapper.selectById(noteId);
        if (scoutNote == null) {
            log.warn("修改失败：球探笔记不存在，noteId={}", noteId);
            throw new RuntimeException("球探笔记不存在，noteId=" + noteId);
        }
        validateUpdate(request);
        scoutNote.setContent(request.content().trim());
        scoutNote.setPotentialRating(request.potentialRating());
        if (request.noteDate() != null) {
            scoutNote.setNoteDate(request.noteDate());
        }
        scoutNoteMapper.updateScoutNote(scoutNote);
        log.info("修改球探笔记成功：noteId={}", noteId);
        return scoutNoteMapper.selectById(noteId);
    }

    public void deleteScoutNote(Integer noteId) {
        log.info("===== 删除球探笔记 =====");
        log.info("删除参数：noteId={}", noteId);
        int deleted = scoutNoteMapper.deleteById(noteId);
        if (deleted == 0) {
            log.warn("删除失败：球探笔记不存在，noteId={}", noteId);
            throw new RuntimeException("球探笔记不存在，noteId=" + noteId);
        }
        log.info("删除球探笔记成功：noteId={}, 影响行数={}", noteId, deleted);
    }

    private void validateUpdate(UpdateScoutNoteRequest request) {
        if (request.content() == null || request.content().isBlank()) {
            throw new RuntimeException("笔记内容不能为空");
        }
        if (request.potentialRating() == null) {
            throw new RuntimeException("潜力评分不能为空");
        }
        if (request.potentialRating() < 1 || request.potentialRating() > 10) {
            throw new RuntimeException("潜力评分必须在 1 到 10 之间");
        }
    }
}
