package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Kanjidic2 {
    public Header header;
    @JacksonXmlElementWrapper(useWrapping = false)
    public KanjiCharacter[] character;
}
