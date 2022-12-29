package com.vincentcodes.jishoapi.service;

import com.vincentcodes.jishoapi.entity.JishoNote;
import com.vincentcodes.jishoapi.repository.JishoNotesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class JishoNotesService {
    @Autowired
    private JishoNotesDao repo;

    public int reloadRepo(){
        return repo.reload();
    }

    public List<JishoNote> getNotes(int amount){
        return repo.getNotes(amount);
    }

    public JishoNote getRandomNote(String searchString, boolean returnMatchingLines){
        return repo.getRandomNote(searchString, returnMatchingLines);
    }

    public List<JishoNote> searchNote(String searchString, boolean returnMatchingLines, boolean includePitch){
        return repo.getNotesWhereNotesMatches(searchString, returnMatchingLines, includePitch);
    }

    public JishoNote getNoteById(int id){
        return repo.getNoteById(id);
    }

    public List<JishoNote> getNotesByIds(Set<Integer> ids){
        return repo.getNotesByIds(ids);
    }

    public List<String> getTopics(){
        return repo.getTopics();
    }
}
