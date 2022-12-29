package com.vincentcodes.jishoapi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincentcodes.jishoapi.entity.JishoNote;
import com.vincentcodes.jishoapi.entity.note.ShirabeJishoRoot;
import com.vincentcodes.jishoapi.helpers.JishoSearchExpressionProcessor;
import com.vincentcodes.jishoapi.helpers.MathUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Currently this thing is read only. Code here is no longer maintained.
 */
@Deprecated
public class JishoNotesRepo implements JishoNotesDao{
    private final String QUOTE_SEPARATOR = "—"; // eg. I am stupid — Vincent
    private final String MEMO_PATH;

    private List<JishoNote> repo;

    public JishoNotesRepo(String memoPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.MEMO_PATH = memoPath;
        ShirabeJishoRoot memoRoot = mapper.readValue(new File(MEMO_PATH), ShirabeJishoRoot.class);
        repo = memoRoot.ShirabeJisho.Bookmarks.list;
    }

    @Override
    public int reload() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ShirabeJishoRoot memoRoot = mapper.readValue(new File(MEMO_PATH), ShirabeJishoRoot.class);
            repo = memoRoot.ShirabeJisho.Bookmarks.list;
            return repo.size();
        }catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<JishoNote> getNotes(int amount){
        return repo.stream().limit(amount).collect(Collectors.toList());
    }

    @Override
    public JishoNote getRandomNote(String searchString, boolean matchingLinesOnly) {
        List<JishoNote> result = getNotesWhereNotesMatches(searchString, matchingLinesOnly, true)
                .stream().filter(ele -> ele.getNote().length() >= 15).collect(Collectors.toList());
        return result.get(MathUtils.getRandomInt(result.size()));
    }

    @Override
    public List<JishoNote> getNotesWhereNotesMatches(String searchString) {
        return getNotesWhereNotesMatches(searchString, false, false);
    }

    @Override
    public List<JishoNote> getNotesWhereNotesMatches(String searchString, boolean matchingLinesOnly, boolean includePitch) {
        if(!matchingLinesOnly)
            return repo.stream().filter((note) -> JishoSearchExpressionProcessor.matches(note.getNote(), searchString)).collect(Collectors.toList());
        return repo.stream()
                .filter((note) -> JishoSearchExpressionProcessor.matches(note.getNote(), searchString))
                .map((note) -> {
                    String result = "";
                    String noteString = note.getNote();
                    String[] lines = noteString.split("\n");
                    boolean firstCharIsNumber = (noteString.charAt(0) >= '0' && noteString.charAt(0) <= '9') || noteString.charAt(0) == '-';
                    if(includePitch && firstCharIsNumber)
                        result += lines[0] + "\n...\n";
                    result += Arrays.stream(lines)
                            .filter(line -> JishoSearchExpressionProcessor.matches(line, searchString))
                            .collect(Collectors.joining("\n"));
                    return new JishoNote(note.getType(), note.getValue(), result);
                }).collect(Collectors.toList());
    }

    @Override
    public JishoNote getNoteById(int id) {
        return repo.stream().filter((note) -> note.getValue() == id).findFirst().orElse(null);
    }

    @Override
    public List<JishoNote> getNotesByIds(Set<Integer> ids){
        return repo.stream().filter((note) -> ids.contains(note.getValue())).collect(Collectors.toList());
    }

    @Override
    public List<String> getTopics() {
        Set<String> result = new HashSet<>();
        repo.stream().filter(note -> note.getNote().contains(QUOTE_SEPARATOR))
            .forEach(note -> {
                String noteString = note.getNote();
                String[] lines = noteString.split("\n");
                result.addAll(Arrays.stream(lines).filter(line -> line.contains(QUOTE_SEPARATOR))
                        .map(line  -> line.substring(line.lastIndexOf(QUOTE_SEPARATOR)+1).trim())
                        .map(topic -> {
                            if(topic.contains("("))
                                return topic.substring(0, topic.indexOf("(")).trim();
                            return topic;
                        })
                        .filter(line -> !line.isEmpty()).collect(Collectors.toList()));
            });
        return new ArrayList<>(result);
    }
}
