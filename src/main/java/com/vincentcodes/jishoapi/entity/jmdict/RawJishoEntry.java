package com.vincentcodes.jishoapi.entity.jmdict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.vincentcodes.jishoapi.helpers.XmlElement;

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

    public int getEnt_seq() {
        return ent_seq;
    }

    public K_ele[] getK_ele() {
        return k_ele;
    }

    public R_ele[] getR_ele() {
        return r_ele;
    }

    public Sense[] getSense() {
        return sense;
    }

    @Deprecated
    public static RawJishoEntry parse(XmlElement element){
        RawJishoEntry entry = new RawJishoEntry();
        entry.ent_seq = Integer.parseInt(element.getFirstElementByTagName("ent_seq").getContent());
        entry.k_ele = K_ele.extractFrom(element.getElementsByTagName("k_ele"));
        entry.r_ele = R_ele.extractFrom(element.getElementsByTagName("r_ele"));
        entry.sense = Sense.extractFrom(element.getElementsByTagName("sense"));
        return entry;
    }
}

