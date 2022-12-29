package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Misc {
    public String grade;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] stroke_count;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] variant;
    public String freq;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] rad_name;
    public String jlpt;
}
