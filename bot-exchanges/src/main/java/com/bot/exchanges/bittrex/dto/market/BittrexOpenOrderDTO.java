package com.bot.exchanges.bittrex.dto.market;

import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BittrexOpenOrderDTO extends OpenOrderDTO {

    @Override
    @JsonSetter("Uuid")
    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }

    @Override
    @JsonSetter("Exchange")
    public void setMarket(String market) {
        super.setMarket(market);
    }

    @Override
    @JsonSetter("Limit")
    public void setLimit(BigDecimal limit) {
        super.setLimit(limit);
    }

    @Override
    @JsonSetter("Opened")
    public void setOpened(LocalDateTime opened) {
        super.setOpened(opened);
    }

    @Override
    @JsonSetter("OrderType")
    public void setOrderType(String orderType) {
        super.setOrderType(orderType);
    }

    @Override
    @JsonSetter("Price")
    public void setPrice(BigDecimal price) {
        super.setPrice(price);
    }

    @Override
    @JsonSetter("PricePerUnit")
    public void setPricePerUnit(BigDecimal pricePerUnit) {
        super.setPricePerUnit(pricePerUnit);
    }

    @Override
    @JsonSetter("Quantity")
    public void setQuantity(BigDecimal quantity) {
        super.setQuantity(quantity);
    }
}
