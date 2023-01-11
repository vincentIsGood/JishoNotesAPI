package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.entity.jmdict.RawJishoEntry;

import java.util.Arrays;

/**
 * @see JishoEntryDetailed
 */
@Deprecated
public class JishoEntryImpl implements JishoEntry{
    private final int sequence; // id of an entry
    private final String[] headword;
    private final String[] readings;
    private final String[] meanings;

    public JishoEntryImpl(int sequence, String[] headword, String[] readings, String[] meanings) {
        this.sequence = sequence;
        this.headword = headword;
        this.readings = readings;
        this.meanings = meanings;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    @Override
    public RawJishoEntry getEntry() {
        return null;
    }

    @Override
    public String[] getHeadword() {
        return headword;
    }

    @Override
    public String[] getReadings() {
        return readings;
    }

    @Override
    public String[] getMeanings() {
        return meanings;
    }

    public String toString(){
        return String.format("{sequence: %d, headword: %s, readings: %s, meanings: %s}",
                sequence,
                Arrays.toString(headword),
                Arrays.toString(readings),
                Arrays.toString(meanings));
    }
}
