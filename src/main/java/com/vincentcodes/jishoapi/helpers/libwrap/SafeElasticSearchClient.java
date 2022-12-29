package com.vincentcodes.jishoapi.helpers.libwrap;

import com.vincentcodes.jishoapi.helpers.ConnectionUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Wrapper for {@link org.elasticsearch.client.RestClient}
 */
@SuppressWarnings("deprecation")
public class SafeElasticSearchClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(SafeElasticSearchClient.class);

    private final String host;
    private final int port;

    private final RestClient client;
    private final RestHighLevelClient highLevelClient;

    public SafeElasticSearchClient(RestClient client, String host, int port) {
        this.client = client;
        this.host = host;
        this.port = port;
        this.highLevelClient = new RestHighLevelClientBuilder(client)
                .setApiCompatibilityMode(true)
                .build();
    }

    public boolean isConnected(){
        return ConnectionUtils.isReachable(host, port);
    }

    // Making low level requests
    /**
     * @return null if error occurred except for ResponseException
     */
    public HighLevelResponse performRequest(Request request){
        try{
            return new HighLevelResponse(client.performRequest(request));
        }catch (ResponseException e){
            return new HighLevelResponse(e.getResponse());
        }catch (IOException e){
            LOGGER.error("Cannot perform request", e);
            return null;
        }
    }

    public Optional<HighLevelResponse> performRequestSafe(Request request){
        return Optional.ofNullable(performRequest(request));
    }

    public Cancellable performRequestAsync(Request request, ResponseListener listener){
        return client.performRequestAsync(request, listener);
    }

    // Making high level requests
    public SearchResponse search(SearchRequest searchRequest){
        try {
            return highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            LOGGER.error("Cannot perform search request.", e);
            return null;
        }
    }
    public SearchResponse search(String index, QueryBuilder queryBuilder){
        SearchSourceBuilder builder = new SearchSourceBuilder().query(queryBuilder).size(10_000);
        return search(new SearchRequest(index).source(builder));
    }
    public SearchResponse search(String index, QueryBuilder queryBuilder, int size){
        SearchSourceBuilder builder = new SearchSourceBuilder().query(queryBuilder).size(size);
        return search(new SearchRequest(index).source(builder));
    }

    /**
     * A higher level way of doing pagination.
     */
    public ElasticRepoPage createPage(String index, QueryBuilder queryBuilder, int size){
        return new ElasticRepoPage(this, index, queryBuilder, size);
    }
    /**
     * Make the very first request where you can get the scroll id.
     * @see #createPage(String, QueryBuilder, int)
     */
    public SearchResponse scrollSearch(String index, QueryBuilder queryBuilder, int size){
        SearchSourceBuilder builder = new SearchSourceBuilder().query(queryBuilder).size(size);
        SearchRequest request = new SearchRequest(index).source(builder);
        request.scroll(TimeValue.timeValueMinutes(1L));
        return search(request);
    }
    /**
     * Retrieve scroll id from SearchResponse and use it to get the next batch of search results
     * @return null if  searchResponse has no scroll id OR an error occurred.
     */
    public SearchResponse scrollSearch(SearchResponse searchResponse){
        return scrollSearch(searchResponse.getScrollId());
    }
    public SearchResponse scrollSearch(String scrollId){
        if(scrollId == null) return null;
        try{
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(TimeValue.timeValueMinutes(1L));
            return highLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Cannot perform search request.", e);
            e.printStackTrace();
            return null;
        }
    }

    public GetResponse getRecordById(String index, String id){
        try{
            return highLevelClient.get(new GetRequest(index, id), RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Cannot perform search request.", e);
            e.printStackTrace();
            return null;
        }
    }
    public MultiGetResponse getRecordsByIds(String index, String[] ids){
        try{
            MultiGetRequest request = new MultiGetRequest();
            for(String id : ids)
                request.add(index, id);
            return highLevelClient.mget(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Cannot perform search request.", e);
            e.printStackTrace();
            return null;
        }
    }
    public MultiGetResponse getRecordsByIds(String index, Collection<String> ids){
        try{
            MultiGetRequest request = new MultiGetRequest();
            for(String id : ids)
                request.add(index, id);
            return highLevelClient.mget(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Cannot perform search request.", e);
            e.printStackTrace();
            return null;
        }
    }

    public CountResponse count(String[] indices, QueryBuilder queryBuilder){
        try {
            if(queryBuilder == null)
                return highLevelClient.count(new CountRequest(indices), RequestOptions.DEFAULT);
            return highLevelClient.count(new CountRequest(indices).query(queryBuilder), RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("Cannot perform search request.", e);
            return null;
        }
    }
    public CountResponse count(String index, QueryBuilder queryBuilder){
        return count(new String[]{index}, queryBuilder);
    }
    public CountResponse count(String index){
        return count(new String[]{index}, null);
    }

    /**
     * Create a bulk processor for making bulk requests
     */
    public SimpleBulkProcessor createBulkProcessor(String index){
        return new SimpleBulkProcessor(highLevelClient, index);
    }

    public IndicesClient indices(){
        return highLevelClient.indices();
    }

    public RestClient getClient() {
        return client;
    }

    public RestHighLevelClient getHighLevelClient(){
        return highLevelClient;
    }
}
