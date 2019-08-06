package com.bot.exchanges.bittrex.dto.account;

import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BittrexOrderHistoryDTO extends OrderHistoryDTO {

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
    @JsonSetter("TimeStamp")
    public void setOriginalDate(LocalDateTime originalDate) {
        super.setOriginalDate(originalDate);
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
