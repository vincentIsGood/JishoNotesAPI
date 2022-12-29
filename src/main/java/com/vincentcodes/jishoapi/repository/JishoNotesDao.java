package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.JishoNote;

import java.util.List;
import java.util.Set;

public interface JishoNotesDao {
    /**
     * @return the new amount of notes
     */
    int reload();

    List<JishoNote> getNotes(int amount);

    JishoNote getRandomNote(String searchString, boolean matchingLinesOnly);

    List<JishoNote> getNotesWhereNotesMatches(String searchString);

    List<JishoNote> getNotesWhereNotesMatches(String searchString, boolean matchingLinesOnly, boolean includePitch);

    JishoNote getNoteById(int id);

    List<JishoNote> getNotesByIds(Set<Integer> ids);

    List<String> getTopics();
}
