package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.entity.jmdict.RawJishoEntry;

import java.util.Arrays;

@JsonView({DetailedJishoEntry.Views.NEW.class})
public class DetailedJishoEntry implements JishoEntry {
    private final RawJishoEntry entry;

    public DetailedJishoEntry(RawJishoEntry entry){
        this.entry = entry;
    }

    @Override
    @JsonView({DetailedJishoEntry.Views.OLD.class, DetailedJishoEntry.Views.NEW.class})
    public int getSequence() {
        return entry.ent_seq;
    }

    public RawJishoEntry getEntry(){
        return entry;
    }

    // Old API down below
    @Override
    @JsonView({DetailedJishoEntry.Views.OLD.class})
    public String[] getHeadword() {
        if(entry.k_ele == null)
            return new String[0];
        return Arrays.stream(entry.k_ele).map(k_ele -> k_ele.keb).toArray(String[]::new);
    }

    @Override
    @JsonView({DetailedJishoEntry.Views.OLD.class})
    public String[] getReadings() {
        if(entry.r_ele == null)
            return new String[0];
        return Arrays.stream(entry.r_ele).map(r_ele -> r_ele.reb).toArray(String[]::new);
    }

    @Override
    @JsonView({DetailedJishoEntry.Views.OLD.class})
    public String[] getMeanings() {
        if(entry.sense == null)
            return new String[0];
        return Arrays.stream(entry.sense).map(sense -> sense.gloss).flatMap(Arrays::stream).toArray(String[]::new);
    }

    public static class Views{
        public static class NEW{}
        public static class OLD{}
    }
}
