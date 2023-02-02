package com.vincentcodes.jishoapi.controller;

import com.vincentcodes.jishoapi.entity.NewsInfoDto;
import com.vincentcodes.jishoapi.service.NewsInfoService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("jishonotes/v1/news")
public class NewsInfoController {

    @Value("${other-private.special-pass}")
    private String PASS;

    @Autowired
    private NewsInfoService service;

    @GetMapping("/old")
    public NewsInfoDto getOldNews(@RequestParam("id") String id){
        Optional<NewsInfoDto> oldNews = service.getOldNews(id);
        if(oldNews.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return oldNews.get();
    }

    @GetMapping
    public List<NewsInfoDto> getNews(
            @RequestParam("len") int length,
            @RequestParam(value = "x", required = false, defaultValue = "[]") Set<String> excludeIds){
        if(excludeIds.size() == 0)
            return List.of();
        return service.getNews(length, excludeIds);
    }

    @PostMapping
    public List<NewsInfoDto> getNewsFromTopics(
            @RequestParam("len") int length,
            @RequestParam("topics") Set<String> topics,
            @RequestBody(required = false) Set<String> excludeIds){
        if(excludeIds == null)
            excludeIds = new HashSet<>();
        return service.getNewsFromTopics(length, excludeIds, topics);
    }

    @PostMapping("/post")
    public void postNewsByCurator(@RequestParam("p") String pass, @RequestBody NewsInfoDto[] news){
        if(!pass.equals(PASS))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        service.postNewsByCurator(news);
    }

    @GetMapping("/topics")
    public Set<String> getTopics(){
        return service.getTopics();
    }
}
