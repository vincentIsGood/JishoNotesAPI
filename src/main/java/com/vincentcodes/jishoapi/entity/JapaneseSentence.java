package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.sterotype.DtoAsWell;
import org.elasticsearch.client.indices.AnalyzeResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DtoAsWell
public class JapaneseSentence {
    public final String sentence;
    public final List<JpVocabulary> vocabs;

    public JapaneseSentence(String sentence, List<JpVocabulary> vocabs) {
        this.sentence = sentence;
        this.vocabs = vocabs;
    }

    public static JapaneseSentence fromAnalyzeResponse(String originalSentence, AnalyzeResponse response){
        // [1]kuromoji_baseform, [2]kuromoji_part_of_speech
        AnalyzeResponse.AnalyzeToken[] tokens = response.detail().tokenfilters()[2].getTokens();
        List<JpVocabulary> vocabs = Arrays.stream(tokens).map(token -> {
            Map<String, Object> attributes = token.getAttributes();
            return new JpVocabulary(token.getTerm(),
                    (String) attributes.get("reading"),
                    (String) attributes.get("partOfSpeech"),
                    (String) attributes.get("inflectionType"),
                    token.getStartOffset(),
                    token.getEndOffset());
        }).collect(Collectors.toList());
        return new JapaneseSentence(originalSentence, vocabs);
    }
}
