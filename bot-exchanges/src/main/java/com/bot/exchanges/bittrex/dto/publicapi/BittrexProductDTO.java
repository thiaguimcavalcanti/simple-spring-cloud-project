package com.bot.exchanges.bittrex.dto.publicapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BittrexProductDTO {
    @JsonProperty("MarketCurrency")
    private String marketCurrency;

    @JsonProperty("BaseCurrency")
    private String baseCurrency;

    @JsonProperty("IsActive")
    private boolean active;
}
