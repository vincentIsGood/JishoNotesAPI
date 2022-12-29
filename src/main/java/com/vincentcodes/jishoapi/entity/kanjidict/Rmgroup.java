package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.util.Arrays;

/**
 * https://stackoverflow.com/questions/23632419/how-to-deserialize-xml-with-annotations-using-fasterxml
 */
@JsonDeserialize(using = JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rmgroup implements IRmgroup{
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] reading;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] meaning;

    @Override
    public String[] getMeaning() {
        return meaning;
    }

    @Override
    public String[] getReading() {
        return reading;
    }

    // Whenever we encounter <reading>, we will do the manual job.
    @JacksonXmlProperty(localName = "reading")
    private void setReading(Reading[] readingType) {
        reading = Arrays.stream(readingType)
                .filter(reading1 -> reading1.r_type != null &&
                        (reading1.r_type.equals("ja_on") || reading1.r_type.equals("ja_kun")))
                .map(reading1 -> reading1.value)
                .toArray(String[]::new);
    }

    // Whenever we encounter <meaning>, we will do the manual job.
    @JacksonXmlProperty(localName = "meaning")
    private void setMeaning(Meaning[] meaningType) {
        meaning = Arrays.stream(meaningType)
                .filter(reading1 -> reading1.m_lang == null)
                .map(reading1 -> reading1.value)
                .toArray(String[]::new);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meaning {
        // <meaning m_lang="">value</meaning>
        @JacksonXmlProperty(isAttribute = true)
        public String m_lang;

        @JacksonXmlText
        public String value;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Reading {
        @JacksonXmlProperty(isAttribute = true)
        public String r_type;

        @JacksonXmlText
        public String value;
    }
}

