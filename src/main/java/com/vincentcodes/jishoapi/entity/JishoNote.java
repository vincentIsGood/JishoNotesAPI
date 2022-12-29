package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincentcodes.jishoapi.utils.DtoAsWell;

@DtoAsWell
public class JishoNote {
    private int type;
    private int value;
    private String note;
    
    public JishoNote(@JsonProperty("type") int type,
                     @JsonProperty("value") int value,
                     @JsonProperty("note") String note) {
        this.type = type;
        this.value = value;
        this.note = note;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }

    public String toString(){
        return String.format("{type: %d, value: %d, note: %s}", type, value, note);
    }
}