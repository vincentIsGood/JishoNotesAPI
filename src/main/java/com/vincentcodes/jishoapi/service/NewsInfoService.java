package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.entity.NewsInfoDto;
import com.vincentcodes.jishoapi.repository.NewsInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NewsInfoService {
    @Autowired
    private NewsInfoDao repo;

    public Optional<NewsInfoDto> getOldNews(String id){
        return repo.getNews(id);
    }

    public List<NewsInfoDto> getNews(int length, Set<String> excludeIds){
        return repo.getRecentNews(length, excludeIds);
    }

    public List<NewsInfoDto> getNewsFromTopics(int length, Set<String> excludeIds, Set<String> topics){
        return repo.getRecentNewsFromTopics(length, excludeIds, topics);
    }

    public void postNewsByCurator(NewsInfoDto[] news){
        repo.postNews(news);
    }

    public Set<String> getTopics(){
        return repo.getTopics();
    }
}
