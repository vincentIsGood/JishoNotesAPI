package com.vincentcodes.jishoapi.helpers.libwrap;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

public class ElasticRepoPage {
    private final SafeElasticSearchClient client;
    private final int pageSize;

    private long nextPageStartingIndex = 0;
    private String currentScrollId;
    private SearchResponse previousResponse;

    private final String index;
    private final QueryBuilder queryBuilder;

    /**
     * Lazy initialization policy is adopted.
     */
    public ElasticRepoPage(SafeElasticSearchClient client, String index, QueryBuilder queryBuilder, int pageSize){
        this.client = client;
        this.index = index;
        this.queryBuilder = queryBuilder;
        this.pageSize = pageSize;
    }
    public ElasticRepoPage(SafeElasticSearchClient client, String index, QueryBuilder queryBuilder){
        this(client, index, queryBuilder, 10);
    }

    /**
     * Read next page.
     */
    public SearchResponse read(){
        if(currentScrollId == null)
            return previousResponse = readFirstPage();
        SearchResponse res = client.scrollSearch(currentScrollId);
        updateStateUsing(res);
        previousResponse = res;
        return res;
    }

    /**
     * @param index starting from 0
     */
    public SearchResponse getToPageHavingIndex(long index){
        if(index <= nextPageStartingIndex)
            throw new IllegalArgumentException("index must not be smaller or equal to total records read");
        while(nextPageStartingIndex < index) read();
        if(previousResponse == null)
            return read();
        return previousResponse;
    }

    /**
     * @param index starting from 0
     */
    public SearchHit getSearchHitByIndex(long index){
        SearchResponse res = getToPageHavingIndex(index);
        // I want current page, not next page.
        return res.getHits().getAt((int)(index - Math.max(nextPageStartingIndex - pageSize, 0)));
    }

    public int getPageSize(){
        return pageSize;
    }

    public long getNextPageStartingIndex() {
        return nextPageStartingIndex;
    }

    public String getCurrentScrollId() {
        return currentScrollId;
    }

    private SearchResponse readFirstPage(){
        SearchResponse res = client.scrollSearch(index, queryBuilder, pageSize);
        updateStateUsing(res);
        return res;
    }

    private void updateStateUsing(SearchResponse res){
        currentScrollId = res.getScrollId();
        nextPageStartingIndex += res.getHits().getHits().length;
    }
}
