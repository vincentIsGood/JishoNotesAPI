package com.vincentcodes.jishoapi.helpers.dict;

import com.ctc.wstx.api.WstxInputProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.vincentcodes.jishoapi.entity.JishoEntryDetailed;
import com.vincentcodes.jishoapi.entity.JishoEntry;
import com.vincentcodes.jishoapi.entity.jmdict.JmdictXmlEntityResolver;
import com.vincentcodes.jishoapi.entity.jmdict.RawJishoEntry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Reads the entries from a provided xml.
 */
public class JishoEntryExtractor {
    private final Map<Integer, JishoEntry> idToEntryMap;
    private final String outJsonPath;

    public JishoEntryExtractor(String pathToXml, String outJsonPath) {
        idToEntryMap = new HashMap<>();
        this.outJsonPath = outJsonPath;

        preload(new File(pathToXml));
    }
    public JishoEntryExtractor(String pathToXml) throws IOException {
        this(pathToXml, pathToXml + ".json");
    }

    @Deprecated
    private void preloadOld(StringBuilder rawXml){
        int entryPoint = 0;
        while((entryPoint = rawXml.indexOf("<entry>", entryPoint)) != -1){
            int closingEntryPoint = rawXml.indexOf("</entry>", entryPoint) + "</entry>".length();
            JishoEntry entry = JishoEntryConverter.createEntryFromString(rawXml.substring(entryPoint, closingEntryPoint));
            idToEntryMap.put(entry.getSequence(), entry);
            entryPoint++;
        }
    }

    /**
     * https://stackoverflow.com/questions/63804258/jackson-xml-undeclared-general-entity-caused-by-custom-entity
     */
    private void preload(File pathToXml){
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.getFactory().getXMLInputFactory().setProperty(
                WstxInputProperties.P_UNDECLARED_ENTITY_RESOLVER, 
                new JmdictXmlEntityResolver());

        List<RawJishoEntry> entries = null;
        catch_block:
        try {
            File jsonOutputFile = new File(this.outJsonPath);
            if(jsonOutputFile.exists()){
                entries = jsonObjectMapper.readValue(jsonOutputFile, new TypeReference<>(){});
                break catch_block;
            }
            entries = xmlMapper.readValue(pathToXml, new TypeReference<>(){});
            jsonObjectMapper.writeValue(jsonOutputFile, entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(entries == null) return;
        List<JishoEntryDetailed> detailedEntries = entries.stream().map(JishoEntryDetailed::new).collect(Collectors.toList());
        Map<Integer, JishoEntry> result = detailedEntries.stream().map(entry -> (JishoEntry)entry).collect(Collectors.toMap(JishoEntry::getSequence, Function.identity()));
        idToEntryMap.putAll(result);
    }

    /**
     * @param seq id of that entry
     * @return null if not found
     */
    // This is slow as shit it's recommended to add cache / indexing (Set) for this
    public JishoEntry lookupEntry(int seq){
        for(JishoEntry entry : idToEntryMap.values()){
            if(entry.getSequence() == seq)
                return entry;
        }
        return null;
    }

    /**
     * @return one of the elements maybe null if an invalid num is provided
     */
    public List<JishoEntry> lookupEntries(int[] seqNumbers){
        if(seqNumbers.length == 0)
            return null;
        List<JishoEntry> result = new ArrayList<>();
        for(int seq : seqNumbers){
            result.add(lookupEntry(seq));
        }
        return result;
    }

    // Search by String
    /**
     * @return null if not found
     */
    public List<JishoEntry> lookupExactEntries(String searchString){
        List<JishoEntry> result = new ArrayList<>();
        for(JishoEntry entry : idToEntryMap.values()){
            if(stringArrayContains(entry.getHeadword(), searchString)
            || stringArrayContains(entry.getReadings(), searchString)){
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * @return A bunch of matching entries.
     * An empty list is returned if none is found.
     */
    public List<JishoEntry> lookupEntries(String regexString){
        regexString = regexString.replaceAll("\\*", "(.{1,})");
        Pattern pattern = Pattern.compile(regexString);
        List<JishoEntry> result = new ArrayList<>();
        for(JishoEntry entry : idToEntryMap.values()){
            if(stringArrayContains(entry.getHeadword(), pattern)
            || stringArrayContains(entry.getReadings(), pattern)){
                result.add(entry);
            }
        }
        return result;
    }

    private boolean stringArrayContains(String[] arr, String ele){
        for(String str : arr){
            if(str.equals(ele))
                return true;
        }
        return false;
    }
    private boolean stringArrayContains(String[] arr, Pattern pattern){
        for(String str : arr){
            Matcher matcher = pattern.matcher(str);
            if(matcher.find())
                return true;
        }
        return false;
    }
}
