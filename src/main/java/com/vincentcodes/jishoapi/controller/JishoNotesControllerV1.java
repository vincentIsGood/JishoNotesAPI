package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.entity.JishoNote;
import com.vincentcodes.jishoapi.service.JishoNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Deprecated
@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/notes")
public class JishoNotesControllerV1 {
    @Autowired
    private JishoNotesService service;

    @GetMapping("/update")
    public int updateNotes(){
        return service.reloadRepo();
    }

    @GetMapping
    public List<JishoNote> getAllNotes(@RequestParam("count") int amount){
        return service.getNotes(amount);
    }

    /**
     * Get a random note among the searched notes
     */
    @GetMapping("/rand")
    public JishoNote getRandomNote(
            @RequestParam(value = "search", required = false) String searchString,
            @RequestParam(value = "strict", required = false) boolean matchingLinesOnly){
        if(searchString == null)
            searchString = "";
        return service.getRandomNote(searchString, matchingLinesOnly);
    }

    /**
     * Find all notes that fulfills the expression stated
     * inside the searchString.
     */
    @GetMapping("/adv")
    public List<JishoNote> searchNotes(
            @RequestParam("search") String searchString,
            @RequestParam(value = "strict", required = false) boolean matchingLinesOnly,
            @RequestParam(value = "pitch", required = false) boolean includePitch){
        return service.searchNote(searchString, matchingLinesOnly, includePitch);
    }

    @GetMapping("/id/{id}")
    public JishoNote getNoteById(@PathVariable("id") int id){
        return service.getNoteById(id);
    }

    // eg. /ids/1505190,1582670,1430910
    @GetMapping("/ids/{ids}")
    public List<JishoNote> getEntriesByIds(@PathVariable("ids") Set<Integer> ids){
        return service.getNotesByIds(ids);
    }

    @GetMapping("/topics")
    public List<String> getTopics(){
        return service.getTopics();
    }
}
