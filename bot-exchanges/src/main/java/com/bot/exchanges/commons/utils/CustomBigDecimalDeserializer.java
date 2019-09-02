package com.bot.exchanges.commons.utils;

import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CustomBigDecimalDeserializer extends JsonDeserializer<CustomBigDecimal> {

  @Override
  public CustomBigDecimal deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
    ObjectCodec oc = jp.getCodec();
    JsonNode root = oc.readTree(jp);
    String value = root.asText();
    return value != null ? (CustomBigDecimal) CustomBigDecimal.valueOf(value) : null;
  }
}