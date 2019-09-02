package com.bot.exchanges.bittrex.dto.publicapi;

import com.bot.commons.dto.ExchangeProductDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BittrexExchangeProductDTO extends ExchangeProductDTO {

    @Override
    @JsonSetter("MarketCurrency")
    public void setProductId(String productId) { super.setProductId(productId); }

    @Override
    @JsonSetter("BaseCurrency")
    public void setBaseProductId(String baseProductId) { super.setBaseProductId(baseProductId); }

    @Override
    @JsonSetter("IsActive")
    public void setActive(boolean active) { super.setActive(active); }
}
