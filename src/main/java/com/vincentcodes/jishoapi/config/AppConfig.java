package com.vincentcodes.jishoapi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vincentcodes.jishoapi.config.elastic.BasicElasticSearchConfig;
import com.vincentcodes.jishoapi.datafilters.ModificationFilter;
import com.vincentcodes.jishoapi.entity.JishoNote;
import com.vincentcodes.jishoapi.helpers.dict.JishoNotesExtractor;
import com.vincentcodes.jishoapi.helpers.libwrap.SafeElasticSearchClient;
import com.vincentcodes.jishoapi.helpers.libwrap.SimpleBulkProcessor;
import com.vincentcodes.jishoapi.repository.*;
import org.elasticsearch.client.core.CountResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// useless, Spring will search the whole package for @config classes automatically
//@Import({WebSecurityConfig.class, JpaDatabaseConfig.class, ElasticsearchConfig.class})
@Configuration
public class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Value("${spring.dangerous-mode}")
    private boolean isInDangerousMode;

    @Autowired
    //@Qualifier("default")
    @Qualifier("safe")
    private ModificationFilter<String> jishonotesFilter;

    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Bean
    public FlashCardsDao postgresFlashCardsDao(){
        return new FlashCardsPostgreRepo();
    }

    @Bean
    public JishoEntryRepo defaultJishoEntryRepo(
            @Value("${external.dict.jmdict_path}") String jmdictPath,
            @Value("${external.dict.kanjidict_path}") String kanjidicPath) throws IOException {
        return new JishoEntryRepo(jmdictPath, kanjidicPath);
    }

    @Bean
    public JishoNotesExtractor initJishoNotesExtractor(@Value("${external.notes_path}") String memoPath){
        return new JishoNotesExtractor(memoPath);
    }

    /**
     * Setup ElasticSearch index from Spring
     */
    @Bean
    public JishoNotesDao defaultJishoNotesRepo(
            @Qualifier("initJishoNotesExtractor") JishoNotesExtractor notesExtractor,
            @Qualifier("jishoNotesConfig") BasicElasticSearchConfig config,
            SafeElasticSearchClient client) throws IOException {
        if(!client.isConnected()){
            LOGGER.error("Connection to ElasticSearch cannot be established");
            if(isInDangerousMode) {
                LOGGER.warn("Dangerous mode is on. Ignoring unconnected ElasticSearch instance");
                return new JishoNotesElasticRepo();
            }
        }

        CountResponse countResponse = client.count(config.indexName);
        if(countResponse.getCount() > 0)
            return new JishoNotesElasticRepo();

        // May extract this part of the code to a new class
        List<JishoNote> notes = notesExtractor.extract();
        try(SimpleBulkProcessor bulkProcessor = client.createBulkProcessor(config.indexName)) {
            for (JishoNote note : notes)
                bulkProcessor.index(Integer.toString(note.getValue()),
                        Map.of("note", jishonotesFilter.filter(note.getNote())));
            LOGGER.info("Sent a total of " + notes.size() + " notes to ElasticSearch for indexing");
        }
        return new JishoNotesElasticRepo();
    }

}
