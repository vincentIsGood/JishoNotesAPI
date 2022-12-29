package com.vincentcodes.jishoapi.helpers.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.vincentcodes.jishoapi.entity.kanjidict.KanjiCharacter;
import com.vincentcodes.jishoapi.entity.kanjidict.Kanjidic2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Extracts entries as needed, the extractor will not
 * turn map the whole xml into pojo, since it will be
 * a waste of memory.
 */
// TODO: finish kanjidict controller first
//@Repository
public class KanjidicExtractor {
    private final Map<String, KanjiCharacter> kanjiToEntryMap;
    private final String outJsonPath;

    public KanjidicExtractor(String pathToXml, String outJsonPath) {
        kanjiToEntryMap = new HashMap<>();
        this.outJsonPath = outJsonPath;

        preload(new File(pathToXml));
    }
    public KanjidicExtractor(String pathToXml) throws IOException {
        this(pathToXml, pathToXml + ".json");
    }

    private void preload(File pathToXml){
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();

        List<KanjiCharacter> entries = null;
        catch_block:
        try {
            File jsonOutputFile = new File(this.outJsonPath);
            if(jsonOutputFile.exists()){
                entries = jsonObjectMapper.readValue(jsonOutputFile, new TypeReference<>(){});
                break catch_block;
            }
            Kanjidic2 kanjidic2 = xmlMapper.readValue(pathToXml, Kanjidic2.class);
            entries = Arrays.asList(kanjidic2.character);
            jsonObjectMapper.writeValue(jsonOutputFile, entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(entries == null) return;
        Map<String, KanjiCharacter> result = entries.stream().collect(Collectors.toMap(entry -> entry.literal, Function.identity()));
        kanjiToEntryMap.putAll(result);
    }

    public Map<String, KanjiCharacter> getKanjiToEntryMap(){
        return kanjiToEntryMap;
    }

    public KanjiCharacter findKanji(String oneKanji){
        return kanjiToEntryMap.get(oneKanji);
    }
}
