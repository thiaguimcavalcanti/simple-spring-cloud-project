package com.bot.commons.utils;

import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomBigDecimalSerializer extends StdSerializer<CustomBigDecimal> {

    public CustomBigDecimalSerializer() {
        this(null);
    }

    public CustomBigDecimalSerializer(Class<CustomBigDecimal> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(CustomBigDecimal value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeNumber(value.toString());
    }
}