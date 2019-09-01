package com.bot.exchanges.cryptocompare.dto;

import com.bot.exchanges.commons.entities.Product;
import com.bot.exchanges.cryptocompare.utils.DataToProductDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
public class CryptoCompareCoinListDTO {
    @JsonProperty("BaseImageUrl")
    private String baseImageUrl;

    @JsonProperty("BaseLinkUrl")
    private String baseLinkUrl;

    @JsonProperty("Data")
    @JsonDeserialize(using = DataToProductDeserializer.class)
    private List<Product> data;
}
