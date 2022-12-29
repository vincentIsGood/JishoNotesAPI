package com.vincentcodes.jishoapi.entity.jmdict;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.helpers.XmlElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawJishoEntry {
    public int ent_seq;
    @JacksonXmlElementWrapper(useWrapping = false)
    public K_ele[] k_ele;
    @JacksonXmlElementWrapper(useWrapping = false)
    public R_ele[] r_ele;
    @JacksonXmlElementWrapper(useWrapping = false)
    public Sense[] sense;

    public static RawJishoEntry parse(XmlElement element){
        RawJishoEntry entry = new RawJishoEntry();
        entry.ent_seq = Integer.parseInt(element.getFirstElementByTagName("ent_seq").getContent());
        entry.k_ele = K_ele.extractFrom(element.getElementsByTagName("k_ele"));
        entry.r_ele = R_ele.extractFrom(element.getElementsByTagName("r_ele"));
        entry.sense = Sense.extractFrom(element.getElementsByTagName("sense"));
        return entry;
    }
}

