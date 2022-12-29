package com.vincentcodes.jishoapi.entity.jmdict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vincentcodes.jishoapi.helpers.XmlElement;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class K_ele {
    public String keb;
    public String ke_inf;
    public String ke_pri;

    public static K_ele[] extractFrom(List<XmlElement> elements) {
        return elements.stream().map(element -> {
            K_ele newK_ele = new K_ele();
            XmlElement tmp;
            newK_ele.keb = (tmp = element.getFirstElementByTagName("keb")) == null ? null : tmp.getContent();
            newK_ele.ke_inf = (tmp = element.getFirstElementByTagName("ke_inf")) == null ? null : tmp.getContent();
            newK_ele.ke_pri = (tmp = element.getFirstElementByTagName("ke_pri")) == null ? null : tmp.getContent();
            return newK_ele;
        }).toArray(K_ele[]::new);
    }
}
