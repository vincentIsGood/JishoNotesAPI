package com.vincentcodes.jishoapi.entity;

import com.vincentcodes.jishoapi.utils.DtoAsWell;

@DtoAsWell
public interface JishoEntry {
    int getSequence();

    // Old... properties
    String[] getHeadword();

    String[] getReadings();

    String[] getMeanings();
}
