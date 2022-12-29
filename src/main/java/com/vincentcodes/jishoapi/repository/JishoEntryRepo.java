package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.entity.kanjidict.KanjiCharacter;
import com.vincentcodes.jishoapi.helpers.dict.JishoEntryExtractor;
import com.vincentcodes.jishoapi.helpers.dict.KanjidicExtractor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: to be replaced by elastic search crud repo? (Spring Data)
//@Repository // see config
public class JishoEntryRepo implements JishoEntryDao{
    private final JishoEntryExtractor jishoEntryExtractor;
    private final KanjidicExtractor kanjidicExtractor;

    public JishoEntryRepo(String jmdictPath, String kanjidicPath) throws IOException {
        jishoEntryExtractor = new JishoEntryExtractor(jmdictPath);
        kanjidicExtractor = new KanjidicExtractor(kanjidicPath);
    }

    @Override
    public JishoEntry getEntryFromId(int id) {
        return jishoEntryExtractor.lookupEntry(id);
    }

    @Override
    public List<JishoEntry> getEntriesFromIds(int[] ids) {
        //return extractor.lookupEntries(ids.stream().mapToInt(Integer::intValue).toArray());
        return jishoEntryExtractor.lookupEntries(ids);
    }

    @Override
    public List<JishoEntry> findEntriesFromString(String searchString){
        return jishoEntryExtractor.lookupEntries(searchString);
    }

    @Override
    public List<JishoEntry> findExactEntryFromString(String word){
        return jishoEntryExtractor.lookupExactEntries(word);
    }

    @Override
    public List<KanjiCharacter> searchMultipleKanji(String[] kanjiArray) {
        return Arrays.stream(kanjiArray).map(kanjidicExtractor::findKanji).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<KanjiCharacter> searchKanjiByString(String kanjiStr) {
        return searchMultipleKanji(kanjiStr.split(""));
    }

    @Override
    public KanjiCharacter searchKanji(String kanji) {
        return kanjidicExtractor.findKanji(kanji);
    }
}
