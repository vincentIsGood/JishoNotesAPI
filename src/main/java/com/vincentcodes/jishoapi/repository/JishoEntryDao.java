package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.entity.JishoEntryImpl;
import com.vincentcodes.jishoapi.entity.kanjidict.KanjiCharacter;

import java.util.List;

public interface JishoEntryDao {
    JishoEntry getEntryFromId(int id);

    List<JishoEntry> getEntriesFromIds(int[] ids);

    List<JishoEntry> findEntriesFromString(String searchString);

    List<JishoEntry> findExactEntryFromString(String word);

    List<KanjiCharacter> searchMultipleKanji(String[] kanjiArray);

    List<KanjiCharacter> searchKanjiByString(String kanjiStr);

    KanjiCharacter searchKanji(String kanji);

}
