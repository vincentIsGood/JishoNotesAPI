package com.vincentcodes.jishoapi.subtests;

import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.helpers.dict.JishoEntryExtractor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JishoEntryExtractorTest {
    private JishoEntryExtractor extractor;

    @BeforeAll
    public void setup() throws IOException {
        extractor = new JishoEntryExtractor("./dict/JMdict_e");
    }

    @Test
    public void test_lookupEntry(){
        String expected = "{sequence: 1551380, headword: [立ち上げる, 立ちあげる], readings: [たちあげる], meanings: [to start (something), to start up, to boot (a computer), to launch (a business)]}";
        JishoEntry result = extractor.lookupEntry(1551380);
        assertEquals(1551380, result.getSequence());
        assertEquals("立ちあげる", result.getHeadword()[1]);
        assertEquals("to start up", result.getMeanings()[1]);
    }

    @Test
    public void test_lookupEntry_stringAsInput(){
        List<JishoEntry> result = extractor.lookupEntries("立ちあげる");
        JishoEntry entry = result.get(0);
        assertEquals(1551380, entry.getSequence());
        assertEquals("立ちあげる", entry.getHeadword()[1]);
        assertEquals("to start up", entry.getMeanings()[1]);
    }

    @Test
    public void test_lookupEntries_stringAsInput(){
        List<JishoEntry> result = extractor.lookupEntries("立ち*");
        assertTrue(result.stream().anyMatch(entry -> entry.getSequence() == 1542830));
        assertTrue(result.stream().anyMatch(entry -> entry.getSequence() == 2843319));
    }
}
