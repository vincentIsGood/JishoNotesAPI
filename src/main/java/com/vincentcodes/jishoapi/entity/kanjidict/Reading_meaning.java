package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reading_meaning{
    @JacksonXmlElementWrapper(useWrapping = false)
    public IRmgroup[] rmgroup;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] nanori;

    @Override
    public String toString() {
        return "Reading_meaning{" +
                "rmgroup=" + Arrays.toString(rmgroup) +
                ", nanori=" + Arrays.toString(nanori) +
                '}';
    }
}
