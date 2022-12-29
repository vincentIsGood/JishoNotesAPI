package com.vincentcodes.jishoapi.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XmlElement {
    private final String raw;

    public XmlElement(String raw){
        this.raw = raw;
    }

    public List<XmlElement> getElementsByTagName(String tagName){
        int startingIndex = 0;
        int endingIndex = 0;
        List<XmlElement> returnVal = new ArrayList<>();
        while((startingIndex = raw.indexOf("<" + tagName, startingIndex)) != -1) {
            if ((endingIndex = raw.indexOf("</" + tagName + ">", startingIndex)) != -1) {
                returnVal.add(new XmlElement(raw.substring(startingIndex, endingIndex + ("</" + tagName + ">").length())));
            }
            startingIndex++;
        }
        return returnVal;
    }

    /**
     * @return null if empty
     */
    public XmlElement getFirstElementByTagName(String tagName){
        int startingIndex = -1;
        int endingIndex = -1;
        if((startingIndex = raw.indexOf("<" + tagName)) != -1) {
            if ((endingIndex = raw.indexOf("</" + tagName + ">", startingIndex)) != -1) {
                return new XmlElement(raw.substring(startingIndex, endingIndex + ("</" + tagName + ">").length()));
            }
        }
        return null;
    }
    public boolean hasVoidTag(String tagName){
        int startingIndex = -1;
        if((startingIndex = raw.indexOf("<" + tagName)) != -1){
            return raw.indexOf("/>", startingIndex) != -1;
        }
        return false;
    }

    public String getRaw(){
        return raw;
    }

    public String getContent(){
        if(raw.length() >= 5){
            boolean isEnclosedByProperTags = raw.charAt(0) == '<' && raw.charAt(raw.length()-1) == '>';
            if(isEnclosedByProperTags){
                return raw.substring(raw.indexOf(">")+1, raw.lastIndexOf("<"));
            }
        }
        return raw;
    }

    public String toString(){
        return raw;
    }
}
