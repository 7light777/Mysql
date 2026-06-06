package com.yjt.basketball.mapper;

import com.yjt.basketball.dto.row.ScoutNoteRow;
import com.yjt.basketball.entity.ScoutNote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoutNoteMapper {

    List<ScoutNoteRow> selectScoutNotes(@Param("playerName") String playerName,
                                        @Param("minRating") Integer minRating,
                                        @Param("maxRating") Integer maxRating,
                                        @Param("keyword") String keyword,
                                        @Param("limit") int limit,
                                        @Param("offset") int offset);

    ScoutNote selectById(@Param("noteId") Integer noteId);

    int insertScoutNote(ScoutNote scoutNote);

    int updateScoutNote(ScoutNote scoutNote);

    int deleteById(@Param("noteId") Integer noteId);
}
