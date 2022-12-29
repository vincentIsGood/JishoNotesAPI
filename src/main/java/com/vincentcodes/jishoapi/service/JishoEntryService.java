package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.entity.JishoEntryImpl;
import com.vincentcodes.jishoapi.entity.kanjidict.KanjiCharacter;
import com.vincentcodes.jishoapi.repository.JishoEntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Business logics, eg. account limits, denying access, and so on.
@Service
public class JishoEntryService {
    @Autowired
    private JishoEntryDao repo;

    public JishoEntry getEntryById(int id){
        return repo.getEntryFromId(id);
    }

    public List<JishoEntry> getEntriesFromIds(int[] ids){
        return repo.getEntriesFromIds(ids);
    }

    public List<JishoEntry> searchEntries(String searchString){
        if(searchString.contains("*"))
            return repo.findEntriesFromString(searchString);
        return repo.findExactEntryFromString(searchString);
    }

    public List<KanjiCharacter> searchKanji(String kanjiStr){
        return repo.searchKanjiByString(kanjiStr);
    }
}
