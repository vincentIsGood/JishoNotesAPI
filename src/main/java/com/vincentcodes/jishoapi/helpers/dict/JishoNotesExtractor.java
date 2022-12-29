package com.vincentcodes.jishoapi.helpers.dict;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentcodes.jishoapi.entity.JishoNote;
import com.vincentcodes.jishoapi.entity.note.ShirabeJishoRoot;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JishoNotesExtractor {
    private final File jsonFile;

    public JishoNotesExtractor(String jsonFilePath){
        this.jsonFile = new File(jsonFilePath);
    }

    public List<JishoNote> extract() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ShirabeJishoRoot memoRoot = mapper.readValue(jsonFile, ShirabeJishoRoot.class);
        return memoRoot.ShirabeJisho.Bookmarks.list;
    }

    public String jsonPath(){
        return jsonFile.getAbsolutePath();
    }
}
