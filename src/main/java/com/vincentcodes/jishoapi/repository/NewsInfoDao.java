package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.NewsInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NewsInfoDao {
    Optional<NewsInfoDto> getNews(String id);

    List<NewsInfoDto> getRecentNews(int length, Set<String> excludeIds);

    List<NewsInfoDto> getRecentNewsFromTopics(int length, Set<String> excludeIds, Set<String> topics);

    void postNews(NewsInfoDto[] news);

    Set<String> getTopics();
}
