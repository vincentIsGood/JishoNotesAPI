package com.vincentcodes.jishoapi.entity.kanjidict;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

//@JsonDeserialize(as = JsonRmgroup.class)
@JsonDeserialize(using = ClassDeterminer.class)
public interface IRmgroup {
    String[] getMeaning();
    String[] getReading();
}

class ClassDeterminer extends JsonDeserializer<IRmgroup> {
    // Endless loop fix:
    // https://stackoverflow.com/questions/32551983/how-use-jackson-objectmapper-inside-custom-deserializer
    @Override
    public IRmgroup deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if(p.getCodec() instanceof XmlMapper)
            return p.readValueAs(Rmgroup.class);
        return p.readValueAs(JsonRmgroup.class);
    }
}