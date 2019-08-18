package com.bot.exchanges.cryptocompare.utils;

import com.bot.exchanges.commons.entities.Product;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataToProductDeserializer extends JsonDeserializer<List<Product>> {

  @Override
  public List<Product> deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
    ObjectCodec oc = jp.getCodec();
    JsonNode root = oc.readTree(jp);

    List<Product> products = new ArrayList<>();

    for (JsonNode node : root) {
      Product product = new Product();
      product.setId(node.get("Symbol").asText());
      product.setName(node.get("CoinName").asText());
      product.setFullName(node.get("FullName").asText());
      if (node.get("ImageUrl") != null && !node.get("ImageUrl").isNull()) {
        product.setImageUrl(node.get("ImageUrl").asText());
      }
      if (node.get("Url") != null && !node.get("Url").isNull()) {
        product.setUrl(node.get("Url").asText());
      }
      products.add(product);
    }

    return products;
  }
}