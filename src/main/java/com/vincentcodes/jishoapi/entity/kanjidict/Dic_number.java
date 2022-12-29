package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dic_number {
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] dic_ref;
}
