package com.vincentcodes.jishoapi.entity.jmdict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.vincentcodes.jishoapi.helpers.XmlElement;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sense {
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] stagk;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] stagr;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] pos;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] xref;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] ant;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] field;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] misc;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] s_inf;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] lsource;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] dial;
    @JacksonXmlElementWrapper(useWrapping = false)
    public String[] gloss;

    public static Sense[] extractFrom(List<XmlElement> elements) {
        return elements.stream().map(element -> {
            Sense newSense = new Sense();
            newSense.stagk = element.getElementsByTagName("stagk").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.stagr = element.getElementsByTagName("stagr").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.pos = element.getElementsByTagName("pos").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.xref = element.getElementsByTagName("xref").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.ant = element.getElementsByTagName("ant").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.field = element.getElementsByTagName("field").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.misc = element.getElementsByTagName("misc").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.s_inf = element.getElementsByTagName("s_inf").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.lsource = element.getElementsByTagName("lsource").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.dial = element.getElementsByTagName("dial").stream().map(XmlElement::getContent).toArray(String[]::new);
            newSense.gloss = element.getElementsByTagName("gloss").stream().map(XmlElement::getContent).toArray(String[]::new);
            return newSense;
        }).toArray(Sense[]::new);
    }
}
