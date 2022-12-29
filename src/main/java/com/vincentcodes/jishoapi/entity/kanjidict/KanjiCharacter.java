package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincentcodes.jishoapi.utils.ReadingUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Often use `literal`, `reading_meaning`. Modify inclusion if needed.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIncludeProperties({"literal", "reading_meaning"})
public class KanjiCharacter {
    public String literal;
    public Codepoint codepoint;
    public Radical radical;
    public Misc misc;
    public Dic_number dic_number;
    public Query_code query_code;
    public Reading_meaning reading_meaning;

    public List<String> getKunyomiReadings(){
        return Arrays.stream(reading_meaning.rmgroup).map(IRmgroup::getReading)
                .flatMap(Arrays::stream).map(reading -> reading.replace(".", ""))
                .filter(reading -> ReadingUtils.isHiragana(reading.substring(0,1)))
                .collect(Collectors.toList());
    }

    public List<String> getOnyomiReadings(){
        return Arrays.stream(reading_meaning.rmgroup).map(IRmgroup::getReading)
                .flatMap(Arrays::stream).map(reading -> reading.replace(".", ""))
                .filter(reading -> ReadingUtils.isKatana(reading.substring(0,1)))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "KanjiCharacter{" +
                "literal='" + literal + '\'' +
                ", reading_meaning=" + reading_meaning +
                '}';
    }
}
