package com.bot.commons.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.bot.commons.utils.DateUtils.convertEpochMilliToZonedDateTime;
import static com.bot.commons.utils.DateUtils.roundZonedDateTimeCeiling;

public class RoundEpochMilliToZonedDateTimeCeilingDeserializer extends StdDeserializer<ZonedDateTime> {

    public RoundEpochMilliToZonedDateTimeCeilingDeserializer() {
        this(null);
    }

    public RoundEpochMilliToZonedDateTimeCeilingDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return roundZonedDateTimeCeiling(convertEpochMilliToZonedDateTime(root.asText()));
    }
}