package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;

/**
 * This class is ONLY used for deserialization for json
 */
@JsonDeserialize(using = JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRmgroup implements IRmgroup{
    public String[] reading;
    public String[] meaning;

    @Override
    public String[] getMeaning() {
        return meaning;
    }

    @Override
    public String[] getReading() {
        return reading;
    }

    @Override
    public String toString() {
        return "JsonRmgroup{" +
                "reading=" + Arrays.toString(reading) +
                ", meaning=" + Arrays.toString(meaning) +
                '}';
    }
}
