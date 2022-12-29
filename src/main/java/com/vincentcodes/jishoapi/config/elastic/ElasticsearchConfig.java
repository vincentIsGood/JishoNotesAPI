package com.vincentcodes.jishoapi.config.elastic;

import com.vincentcodes.jishoapi.helpers.libwrap.HighLevelResponse;
import com.vincentcodes.jishoapi.helpers.libwrap.SafeElasticSearchClient;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

@Configuration
public class ElasticsearchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Value("${spring.data.elastic.host}")
    private String host;

    @Value("${spring.data.elastic.port}")
    private int port;

    @Value("${spring.data.elastic.username}")
    private String username;

    @Value("${spring.data.elastic.password}")
    private String password;

    // https://www.baeldung.com/configuration-properties-in-spring-boot
    @Bean
    @ConfigurationProperties(prefix = "spring.data.elastic.jishonotes-index")
    public BasicElasticSearchConfig jishoNotesConfig(){
        return new BasicElasticSearchConfig();
    }

    // find the only method that matches `jishoNotesConfig` which infers `#jishoNotesConfig`
    @Bean
    public SafeElasticSearchClient initRestClient(@Qualifier("jishoNotesConfig") BasicElasticSearchConfig notesConfig)
            throws Exception {
        SafeElasticSearchClient client = new SafeElasticSearchClient(createLowLevelClient(), host, port);
        createSearchIndex(client, notesConfig);
        return client;
    }

    private void createSearchIndex(SafeElasticSearchClient client, BasicElasticSearchConfig config) {
        LOGGER.info("Creating a new index on ElasticSearch with name: " + config.indexName);
        Request indexRequest = new Request("PUT", "/" + config.indexName);
        try {
            indexRequest.setJsonEntity(new String(new FileInputStream(config.getIndexPath()).readAllBytes()));
        }catch (IOException ex){
            throw new UncheckedIOException("Cannot read '"+ config.getIndexPath() +"', please make sure you have one", ex);
        }
        HighLevelResponse res = client.performRequest(indexRequest);
        if(res == null)
            LOGGER.error("Cannot create the index named: " + config.indexName);
        else if(res.getStatusCode() == 400){
            LOGGER.warn("An existing index probably exist: " + res.getBodyAsString());
        }
    }

    // https://stackoverflow.com/questions/48185570/add-authentication-in-elasticsearch-high-level-client-for-java
    // https://stackoverflow.com/questions/57057074/how-to-disable-ssl-verification-for-elasticsearch-restclient-v6-7-0-in-java
    private RestClient createLowLevelClient() throws Exception {
        // Connect Elasticsearch by appending the header "Authorization: Basic ####"
        final CredentialsProvider credentialProvider = new BasicCredentialsProvider();
        credentialProvider.setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));

        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (certs, type)->true).build();

        return RestClient.builder(new HttpHost(host, port, "https"))
                .setHttpClientConfigCallback(asyncClientBuilder -> asyncClientBuilder
                        .setDefaultCredentialsProvider(credentialProvider)
                        .setSSLContext(sslContext)
                        .setSSLHostnameVerifier((a,b)->true))
                .build();
    }

    // https://dzone.com/articles/defining-bean-dependencies-with-java-config-in-spring-framework
    //@Bean
    //public ElasticsearchClient createEsClient(@Qualifier("lowLevelClient") RestClient restClient){
    //    ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
    //    return new ElasticsearchClient(transport);
    //}
}
