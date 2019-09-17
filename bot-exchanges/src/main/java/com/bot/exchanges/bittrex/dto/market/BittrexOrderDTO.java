package com.bot.exchanges.bittrex.dto.market;

import com.bot.commons.dto.OrderDTO;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class BittrexOrderDTO extends OrderDTO {

    @Override
    @JsonSetter("Uuid")
    public void setId(String id) {
        super.setId(id);
    }

    public void setExchange(String symbol) {
        super.setBaseProduct(symbol.substring(0, 3));
        super.setProduct(symbol.substring(4));
    }

    @Override
    @JsonSetter("Opened")
    public void setDate(ZonedDateTime date) {
        super.setDate(date);
    }

    @Override
    @JsonSetter("OrderType")
    public void setOrderType(OrderTypeEnum orderType) {
        super.setOrderType(orderType);
    }

    @Override
    @JsonSetter("Limit")
    public void setPrice(CustomBigDecimal price) {
        super.setPrice(price);
    }

    @Override
    @JsonSetter("PricePerUnit")
    public void setPricePerUnit(CustomBigDecimal pricePerUnit) {
        super.setPricePerUnit(pricePerUnit);
    }

    @Override
    @JsonSetter("Quantity")
    public void setQuantity(CustomBigDecimal quantity) {
        super.setQuantity(quantity);
    }
}
