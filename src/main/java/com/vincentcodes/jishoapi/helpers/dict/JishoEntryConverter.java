package com.vincentcodes.jishoapi.helpers.dict;

import com.vincentcodes.jishoapi.entity.JishoEntryImpl;
import com.vincentcodes.jishoapi.helpers.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Essentially converts xml string entry into JishoEntry
 */
@Deprecated
public class JishoEntryConverter {
    public static JishoEntryImpl createEntryFromString(String xml){
        XmlElement element = new XmlElement(xml);
        element = element.getFirstElementByTagName("entry");
        return new JishoEntryImpl(
                extractSequence(element),
                extractHeadword(element).toArray(String[]::new),
                extractReadings(element).toArray(String[]::new),
                extractMeanings(element).toArray(String[]::new)
        );
    }
    public static JishoEntryImpl createEntryFromString(XmlElement element){
        element = element.getFirstElementByTagName("entry");
        return new JishoEntryImpl(
                extractSequence(element),
                extractHeadword(element).toArray(String[]::new),
                extractReadings(element).toArray(String[]::new),
                extractMeanings(element).toArray(String[]::new)
        );
    }

    public static int extractSequence(XmlElement element){
        return Integer.parseInt(element.getFirstElementByTagName("ent_seq").getContent());
    }

    public static List<String> extractHeadword(XmlElement element){
        List<String> result = new ArrayList<>();
        for(XmlElement ele : element.getElementsByTagName("keb")){
            result.add(ele.getContent());
        }
        return result;
    }

    public static List<String> extractReadings(XmlElement element){
        List<String> result = new ArrayList<>();
        for(XmlElement ele : element.getElementsByTagName("reb")){
            result.add(ele.getContent());
        }
        return result;
    }

    public static List<String> extractMeanings(XmlElement element){
        List<String> result = new ArrayList<>();
        for(XmlElement ele : element.getElementsByTagName("gloss")){
            result.add(ele.getContent());
        }
        return result;
    }
}
