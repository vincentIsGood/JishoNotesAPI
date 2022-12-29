package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.entity.JapaneseSentence;
import com.vincentcodes.jishoapi.repository.JishoNotesElasticRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/analyze")
public class SentenceAnalyzer {
    @Autowired
    private JishoNotesElasticRepo repo;

    @PostMapping
    public JapaneseSentence analyzeSentence(@RequestBody String text){
        return repo.analyzeSentence(text);
    }
}
