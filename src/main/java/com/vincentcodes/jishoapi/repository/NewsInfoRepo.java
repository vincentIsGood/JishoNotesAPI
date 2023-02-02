package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.entity.NewsInfoDto;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * A circular list is used to maintain the news
 */
@Repository
public class NewsInfoRepo implements NewsInfoDao{
    private static final int MAX_SIZE_EACH_TOPIC = 100;

    private final Map<String, NewsInfoDto> repo;

    /**
     * Most recent news is at the tail
     */
    private final Map<String, LinkedList<String>> topicToOrderedIds;

    public NewsInfoRepo() {
        repo = new HashMap<>();
        topicToOrderedIds = new HashMap<>();
    }

    @Override
    public Optional<NewsInfoDto> getNews(String id) {
        return Optional.ofNullable(repo.get(id));
    }

    /**
     * @return news from topic "anime"
     */
    @Override
    public List<NewsInfoDto> getRecentNews(int length, Set<String> excludeIds) {
        LinkedList<String> orderedIds;
        if(topicToOrderedIds.size() == 0) return List.of();
        if(topicToOrderedIds.size() == 1)
            orderedIds = topicToOrderedIds.values().stream().findFirst().get();
        else if(topicToOrderedIds.containsKey("anime"))
            orderedIds = topicToOrderedIds.get("anime");
        else orderedIds = topicToOrderedIds.values().stream().findAny().get();

        List<NewsInfoDto> result = new ArrayList<>();
        Iterator<String> iterator = orderedIds.descendingIterator();
        int count = 0;
        while(iterator.hasNext()){
            String id = iterator.next();
            if(!excludeIds.contains(id))
                result.add(repo.get(id));
            count++;
            if(count >= length) break;
        }
        return result;
    }

    /**
     * @param length number of news for EACH topic
     */
    @Override
    public List<NewsInfoDto> getRecentNewsFromTopics(int length, Set<String> excludeIds, Set<String> topics){
        if(topicToOrderedIds.size() == 0) return List.of();

        List<NewsInfoDto> result = new ArrayList<>();
        for(String topic : topics) {
            if(!topicToOrderedIds.containsKey(topic))
                continue;
            LinkedList<String> orderedIds = topicToOrderedIds.get(topic);
            Iterator<String> iterator = orderedIds.descendingIterator();
            int count = 0;
            while (iterator.hasNext()) {
                String id = iterator.next();
                if (!excludeIds.contains(id))
                    result.add(repo.get(id));
                count++;
                if (count >= length) break;
            }
        }
        return result;
    }

    /**
     * @param news more recent news comes from smaller index (ie. index 0 is the most recent news)
     */
    @Override
    public synchronized void postNews(NewsInfoDto[] news) {
        for(int i = news.length-1; i >= 0; i--) {
            // add news to each of their own topic (with the most recent news being at the largest index)
            NewsInfoDto info = news[i];
            topicToOrderedIds.putIfAbsent(info.type, new LinkedList<>());
            LinkedList<String> orderedIds = topicToOrderedIds.get(info.type);
            if(orderedIds.size() >= MAX_SIZE_EACH_TOPIC) {
                // get the oldest news id and remove it
                repo.remove(orderedIds.removeFirst());
            }
            repo.put(info.id, info);
            orderedIds.add(info.id);
        }
    }

    @Override
    public Set<String> getTopics(){
        return topicToOrderedIds.keySet();
    }
}
