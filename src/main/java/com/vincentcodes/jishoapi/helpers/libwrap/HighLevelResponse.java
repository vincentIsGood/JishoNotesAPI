package com.vincentcodes.jishoapi.helpers.libwrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Wrapper for {@link org.elasticsearch.client.Response}
 */
public class HighLevelResponse {
    public static ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(HighLevelResponse.class);
    private final Response response;

    public HighLevelResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode(){
        return response.getStatusLine().getStatusCode();
    }

    public HttpHost getHost(){
        return response.getHost();
    }

    public String getHeader(String key){
        return response.getHeader(key);
    }

    public String getBodyType(){
        return response.getEntity().getContentType().getValue();
    }

    /**
     * This method will read the whole inputstream
     * @return null if any error occurs
     */
    public String getBodyAsString(){
        try {
            return new String(response.getEntity().getContent().readAllBytes());
        } catch (IOException e) {
            LOGGER.error("Cannot convert http response body to string", e);
            return null;
        }
    }

    /**
     * A jackson object mapper is used. Modify {@link #objectMapper} if needed.
     * @return null if any error occurs
     */
    public <T> T getBodyAsObject(Class<T> clazz){
        try {
            return objectMapper.readValue(response.getEntity().getContent().readAllBytes(), clazz);
        } catch (IOException e) {
            LOGGER.error("Cannot convert http response body to class: " + clazz, e);
            return null;
        }
    }

    public Response getOriginalResponse() {
        return response;
    }
}
