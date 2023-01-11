package com.vincentcodes.jishoapi.entity.jmdict;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.vincentcodes.jishoapi.helpers.XmlElement;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class R_ele {
    public String reb;
    // re_nokanji is not reliable
    @JsonIgnore
    public boolean re_nokanji = false; // if it exist, the method below is called by Jackson to set it true
    public String re_restr;
    public String re_inf;
    @JsonIgnore
    public String re_pri;

    public String getReb() {
        return reb;
    }

    public boolean isRe_nokanji() {
        return re_nokanji;
    }

    public String getRe_restr() {
        return re_restr;
    }

    public String getRe_inf() {
        return re_inf;
    }

    public String getRe_pri() {
        return re_pri;
    }

    // Whenever we encounter <re_nokanji>, we will do the manual job.
    @JacksonXmlProperty(localName = "re_nokanji")
    private void re_nokanjiExist(boolean ignored) {
        this.re_nokanji = true;
    }

    public static R_ele[] extractFrom(List<XmlElement> elements) {
        return elements.stream().map(element -> {
            XmlElement tmp;
            R_ele newR_ele = new R_ele();
            newR_ele.reb = (tmp = element.getFirstElementByTagName("reb")) == null ? null : tmp.getContent();
            newR_ele.re_nokanji = element.hasVoidTag("re_nokanji");
            newR_ele.re_restr = (tmp = element.getFirstElementByTagName("re_restr")) == null ? null : tmp.getContent();
            newR_ele.re_inf = (tmp = element.getFirstElementByTagName("re_inf")) == null ? null : tmp.getContent();
            newR_ele.re_pri = (tmp = element.getFirstElementByTagName("re_pri")) == null ? null : tmp.getContent();
            return newR_ele;
        }).toArray(R_ele[]::new);
    }
}
