package com.bot.exchanges.binance.dto.publicapi;

import com.bot.commons.dto.ExchangeProductDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BinanceExchangeProductDTO extends ExchangeProductDTO {

    @Override
    @JsonSetter("quoteAsset")
    public void setProductId(String productId) { super.setProductId(productId); }

    @Override
    @JsonSetter("baseAsset")
    public void setBaseProductId(String baseProductId) { super.setBaseProductId(baseProductId); }

    public void setStatus(String status) { super.setActive("TRADING".equals(status)); }
}
