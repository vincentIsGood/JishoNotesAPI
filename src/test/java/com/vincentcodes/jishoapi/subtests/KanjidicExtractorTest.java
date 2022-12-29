package com.vincentcodes.jishoapi.subtests;

import com.vincentcodes.jishoapi.helpers.dict.KanjidicExtractor;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KanjidicExtractorTest {
    private KanjidicExtractor extractor;

    @BeforeAll
    public void setup() throws IOException {
        extractor = new KanjidicExtractor("./dict/kanjidic2.xml");
    }

    @Test
    public void test(){
        System.out.println(extractor.getKanjiToEntryMap().get("食").getKunyomiReadings());
        System.out.println(extractor.getKanjiToEntryMap().get("食").getOnyomiReadings());
        assertEquals("食", extractor.getKanjiToEntryMap().get("食").literal);
    }
}
