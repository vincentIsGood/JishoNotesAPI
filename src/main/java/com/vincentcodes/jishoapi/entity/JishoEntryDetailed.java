package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.vincentcodes.jishoapi.entity.jmdict.RawJishoEntry;

import java.util.Arrays;

public class JishoEntryDetailed implements JishoEntry {
    @JsonUnwrapped
    private final RawJishoEntry entry;

    public JishoEntryDetailed(RawJishoEntry entry){
        this.entry = entry;
    }

    @Override
    public int getSequence() {
        return entry.ent_seq;
    }

    @Override
    public RawJishoEntry getEntry(){
        return entry;
    }

    // Old APIs down below
    @Override
    public String[] getHeadword() {
        if(entry.k_ele == null)
            return new String[0];
        return Arrays.stream(entry.k_ele).map(k_ele -> k_ele.keb).toArray(String[]::new);
    }

    @Override
    public String[] getReadings() {
        if(entry.r_ele == null)
            return new String[0];
        return Arrays.stream(entry.r_ele).map(r_ele -> r_ele.reb).toArray(String[]::new);
    }

    @Override
    public String[] getMeanings() {
        if(entry.sense == null)
            return new String[0];
        return Arrays.stream(entry.sense).map(sense -> sense.gloss).flatMap(Arrays::stream).toArray(String[]::new);
    }
}
