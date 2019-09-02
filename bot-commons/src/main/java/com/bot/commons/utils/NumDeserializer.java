package com.bot.commons.utils;

import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.ta4j.core.num.Num;

import java.io.IOException;

public class NumDeserializer extends StdDeserializer<Num> {

    public NumDeserializer() {
        this(null);
    }

    public NumDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Num deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return CustomBigDecimal.valueOf(root.asText());
    }
}