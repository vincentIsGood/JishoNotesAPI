package com.vincentcodes.jishoapi.helpers.libwrap;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Map;

@SuppressWarnings("deprecation")
public class SimpleBulkProcessor implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleBulkProcessor.class);

    private final BulkProcessor bulkProcessor;
    private String indexName;

    public SimpleBulkProcessor(RestHighLevelClient highLevelClient, String indexName) {
        this(highLevelClient, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                LOGGER.debug("Executing bulk [{}] with {} requests", executionId, numberOfActions);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                if (response.hasFailures()) {
                    LOGGER.warn("Bulk [{}] executed with failures", executionId);
                } else {
                    LOGGER.debug("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                LOGGER.error("Failed to execute bulk request", failure);
            }
        }, indexName);
    }
    public SimpleBulkProcessor(RestHighLevelClient highLevelClient, BulkProcessor.Listener bulkListener, String indexName) {
        BulkProcessor.Builder builder = BulkProcessor.builder(
                (req, listener) -> highLevelClient.bulkAsync(req, RequestOptions.DEFAULT, listener),
                bulkListener, "bulk-processor-name");
        bulkProcessor = builder.build();
        this.indexName = indexName;
    }

    public SimpleBulkProcessor setIndexName(String indexName){
        this.indexName = indexName;
        return this;
    }

    public SimpleBulkProcessor add(IndexRequest request){
        bulkProcessor.add(request);
        return this;
    }
    public SimpleBulkProcessor index(String id, Map<String, ?> content){
        return add(new IndexRequest(indexName).id(id).source(content));
    }

    public SimpleBulkProcessor add(DeleteRequest request){
        bulkProcessor.add(request);
        return this;
    }
    public SimpleBulkProcessor delete(String id){
        return add(new DeleteRequest(indexName).id(id));
    }

    @Override
    public void close() {
        bulkProcessor.close();
    }
}
