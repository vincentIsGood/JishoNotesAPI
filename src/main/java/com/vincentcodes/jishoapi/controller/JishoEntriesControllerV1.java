package com.vincentcodes.jishoapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.entity.DetailedJishoEntry;
import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.service.JishoEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/entries")
public class JishoEntriesControllerV1 {
    @Autowired
    private JishoEntryService service;

    @GetMapping("/id/{id}")
    @JsonView(DetailedJishoEntry.Views.OLD.class)
    public JishoEntry getEntryById(@PathVariable("id") int id){
        return service.getEntryById(id);
    }

    // eg. /ids/1505190,1582670,1430910
    @GetMapping("/ids/{ids}")
    @JsonView(DetailedJishoEntry.Views.OLD.class)
    public List<JishoEntry> getEntriesByIds(@PathVariable("ids") int[] ids){
        return service.getEntriesFromIds(ids);
    }

    @GetMapping("/adv")
    @JsonView(DetailedJishoEntry.Views.OLD.class)
    public List<JishoEntry> searchEntries(@RequestParam("search") String searchString){
        return service.searchEntries(searchString);
    }
}
