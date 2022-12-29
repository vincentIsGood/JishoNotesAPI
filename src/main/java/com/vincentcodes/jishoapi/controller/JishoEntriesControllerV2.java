package com.vincentcodes.jishoapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.entity.DetailedJishoEntry;
import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.entity.kanjidict.KanjiCharacter;
import com.vincentcodes.jishoapi.service.JishoEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("jishonotes/v2/entries")
public class JishoEntriesControllerV2 {
    @Autowired
    private JishoEntryService service;

    @GetMapping("/id/{id}")
    @JsonView(DetailedJishoEntry.Views.OLD.class)
    public JishoEntry getEntryById(@PathVariable("id") int id){
        return service.getEntryById(id);
    }

    // eg. [1505190,1582670,1430910]
    /**
     * when ids come from seareching JishoNotes, you may obtain some mismatch sequences due to
     * multiple updates of JMdict. If this is the case, some ids may return null instead of JishoEntry.
     */
    @PostMapping("/ids")
    @JsonView(DetailedJishoEntry.Views.OLD.class)
    public List<JishoEntry> getEntriesByIds(@RequestBody int[] ids){
        return service.getEntriesFromIds(ids);
    }

    @GetMapping("/adv")
    @JsonView(DetailedJishoEntry.Views.OLD.class)
    public List<JishoEntry> searchEntries(@RequestParam("search") String searchString){
        return service.searchEntries(searchString);
    }

    @PostMapping("/kanji")
    public List<KanjiCharacter> searchKanji(@RequestBody String kanji){
        return service.searchKanji(kanji);
    }

}
