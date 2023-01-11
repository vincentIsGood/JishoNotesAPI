package com.vincentcodes.jishoapi.repository;

import com.vincentcodes.jishoapi.config.elastic.BasicElasticSearchConfig;
import com.vincentcodes.jishoapi.entity.JapaneseSentence;
import com.vincentcodes.jishoapi.entity.JishoNote;
import com.vincentcodes.jishoapi.helpers.dict.JishoNotesExtractor;
import com.vincentcodes.jishoapi.helpers.libwrap.ElasticRepoPage;
import com.vincentcodes.jishoapi.helpers.libwrap.SafeElasticSearchClient;
import com.vincentcodes.jishoapi.helpers.libwrap.SimpleBulkProcessor;
import com.vincentcodes.jishoapi.utils.QueryBuilderTemplates;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// It seems almost every database has Transaction prepare/commit/rollback function.
// That's maybe why java.sql.Connection#rollback / #commit exists.
//
// However, ElasticSearch do not support Transactions. That's unfortunate
// https://stackoverflow.com/questions/47350977/rollback-on-elasticsearch-bulk-insert-failure
//@Repository // see config
public class JishoNotesElasticRepo implements JishoNotesDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(JishoNotesElasticRepo.class);

    @Autowired
    private SafeElasticSearchClient restClient;

    @Autowired
    private BasicElasticSearchConfig config;

    @Autowired
    private JishoNotesExtractor notesExtractor;

    private final Random random = new Random();
    private List<String> topics; // laze loaded
    private int notesLength = -1;

    /**
     * @return the previous number of records reside within ElasticSearch
     * (ie. # of records before re-indexing)
     */
    @Override
    public int reload() {
        CompletableFuture.runAsync(() -> {
            List<JishoNote> notes = null;
            try {
                notes = notesExtractor.extract();
                try (SimpleBulkProcessor bulkProcessor = restClient.createBulkProcessor(config.indexName)) {
                    for (JishoNote note : notes)
                        bulkProcessor.index(Integer.toString(note.getValue()), Map.of("note", note.getNote()));
                    LOGGER.info("Sent a total of " + notes.size() + " notes to ElasticSearch for re-indexing");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        CountResponse response = restClient.count(config.indexName, QueryBuilders.matchAllQuery());
        return notesLength = (int)response.getCount();
    }
    // A bean is required for Async beans to work because we need Spring to wrap them in a proxy
    //@Async
    //private CompletableFuture<Integer> reIndexJishonotes(){
    //}

    @Override
    public List<JishoNote> getNotes(int amount) {
        amount = Math.min(amount, 10000);
        SearchResponse response = restClient.search(config.indexName, QueryBuilders.matchAllQuery(), amount);
        return searchResponseToJishoNote(response);
    }

    @Override
    public JishoNote getRandomNote(String searchString, boolean matchingLinesOnly) {
        if(notesLength == -1) reload();
        ElasticRepoPage page = restClient.createPage(config.indexName, QueryBuilders.matchAllQuery(), 1000);
        return searchHitToJishoNote(page.getSearchHitByIndex(random.nextInt(notesLength)));
    }

    @Override
    public List<JishoNote> getNotesWhereNotesMatches(String searchString) {
        return getNotesWhereNotesMatches(searchString, false, false);
    }

    @Override
    public List<JishoNote> getNotesWhereNotesMatches(String searchString, boolean matchingLinesOnly, boolean includePitch) {
        SearchResponse response = restClient.search(config.indexName, QueryBuilderTemplates.multiSearchPhrase(searchString, "note", "note_raw"));
        List<JishoNote> notes = searchResponseToJishoNote(response);
        return notes.stream().map(note -> {
            String[] lines = note.getNote().split("\n");
            String finalNote = "";
            if(note.getNote().charAt(0) >= '0' && note.getNote().charAt(0) <= '9')
                finalNote = lines[0] + "\n...\n";
            return new JishoNote(note.getType(), note.getValue(), finalNote + Arrays.stream(lines)
                    .filter(line -> line.contains(searchString))
                    .collect(Collectors.joining("\n\n")));
        }).collect(Collectors.toList());
    }

    @Override
    public JishoNote getNoteById(int id) {
        return getResponseToJishoNote(restClient.getRecordById(config.indexName, String.valueOf(id)));
    }

    @Override
    public List<JishoNote> getNotesByIds(Set<Integer> ids) {
        return multiGetToJishoNote(restClient.getRecordsByIds(config.indexName, ids.stream().map(String::valueOf).collect(Collectors.toList())));
    }

    @Override
    public List<String> getTopics() {
        if(topics != null) return topics;
        SearchResponse response = restClient.search(config.indexName, QueryBuilderTemplates.multiSearchPhrase("—", "note_raw"));
        List<JishoNote> notes = searchResponseToJishoNote(response);
        return topics = notes.stream().map(jishoNote -> {
            String note = jishoNote.getNote();
            return Arrays.stream(note.split("\n"))
                    .filter(line -> line.contains("—"))
                    .map(line -> {
                        line = line.substring(line.indexOf("—")+1);
                        int indexOfOpenParam;
                        if((indexOfOpenParam = line.indexOf("(")) != -1)
                            return line.substring(0, indexOfOpenParam).trim();
                        return line.trim();
                    })
                    .collect(Collectors.toList());
        }).flatMap(List::stream).distinct().collect(Collectors.toList());
    }

    public JapaneseSentence analyzeSentence(String sentence){
        AnalyzeRequest request = AnalyzeRequest.withIndexAnalyzer(
                        config.getIndexName(),
                        config.normalAnalyzerName(),
                        sentence)
                .explain(true);
        try {
            AnalyzeResponse response = restClient.indices().analyze(request, RequestOptions.DEFAULT);
            return JapaneseSentence.fromAnalyzeResponse(sentence, response);
        } catch (IOException ignore) {}
        return null;
    }

    private List<JishoNote> searchResponseToJishoNote(SearchResponse response){
        return searchResponseToJishoNote(Arrays.stream(response.getHits().getHits()).collect(Collectors.toList()));
    }
    private List<JishoNote> searchResponseToJishoNote(List<SearchHit> searchHits){
        return searchHits.stream().map(this::searchHitToJishoNote).collect(Collectors.toList());
    }
    private JishoNote searchHitToJishoNote(SearchHit hit){
        return new JishoNote(100, Integer.parseInt(hit.getId()), hit.getSourceAsMap().get("note").toString());
    }
    private JishoNote getResponseToJishoNote(GetResponse response) {
        try {
            return new JishoNote(100, Integer.parseInt(response.getId()), response.getSourceAsMap().get("note").toString());
        }catch (RuntimeException e){
            LOGGER.error("Cannot retrieve info from ElasticSearch GetResponse with id: " + response.getId());
            return new JishoNote(100, Integer.parseInt(response.getId()), "Error occurred");
        }
    }
    private List<JishoNote> multiGetToJishoNote(MultiGetResponse response){
        return Arrays.stream(response.getResponses())
                .map(MultiGetItemResponse::getResponse)
                .map(this::getResponseToJishoNote)
                .collect(Collectors.toList());
    }
}
