package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JpVocabulary {
    // Base form (not original)
    public final String baseVocab;

    // Original stuff (taken from original sentence)
    public final String reading;
    public final String partOfSpeech;
    public final String inflectionType;
    public final int startPos;
    public final int endPos; //exclusive

    public JpVocabulary(String baseVocab, String reading, String partOfSpeech, String inflectionType, int startPos, int endPos) {
        this.baseVocab = baseVocab;
        this.reading = reading;
        this.partOfSpeech = partOfSpeech;
        this.inflectionType = inflectionType;
        this.startPos = startPos;
        this.endPos = endPos;
    }
}
