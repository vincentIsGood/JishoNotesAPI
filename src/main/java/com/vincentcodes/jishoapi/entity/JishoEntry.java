package com.vincentcodes.jishoapi.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.vincentcodes.jishoapi.entity.jmdict.RawJishoEntry;
import com.vincentcodes.jishoapi.sterotype.DtoAsWell;

@DtoAsWell
@JsonView({JishoEntry.Views.NEW.class,
        JishoEntry.Views.COMPATIBLE.class,
        JishoEntry.Views.OLD.class})
public interface JishoEntry {
    int getSequence();

    @JsonView({Views.COMPATIBLE.class, Views.NEW.class})
    RawJishoEntry getEntry();

    // Old... properties
    @JsonView({Views.OLD.class, Views.COMPATIBLE.class})
    String[] getHeadword();

    @JsonView({Views.OLD.class, Views.COMPATIBLE.class})
    String[] getReadings();

    @JsonView({Views.OLD.class, Views.COMPATIBLE.class})
    String[] getMeanings();

    class Views{
        public static class OLD{}
        // HYBRID: uses more bytes to send
        public static class COMPATIBLE {}
        public static class NEW{}
    }
}
