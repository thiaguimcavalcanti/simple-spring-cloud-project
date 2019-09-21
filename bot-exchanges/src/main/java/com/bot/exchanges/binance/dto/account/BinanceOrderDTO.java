package com.bot.exchanges.binance.dto.account;

import com.bot.commons.dto.OrderDTO;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.EpochMilliToZonedDateTimeDeserializer;
import com.bot.exchanges.binance.utils.BinanceUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.ZonedDateTime;

import static com.bot.exchanges.binance.utils.BinanceUtils.splitSymbol;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceOrderDTO extends OrderDTO {

    @JsonSetter("symbol")
    public void setSymbol(String symbol) {
        String[] products = splitSymbol(symbol);
        super.setBaseProduct(products[0]);
        super.setProduct(products[1]);
    }

    @JsonSetter("orderId")
    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @JsonSetter("side")
    @Override
    public void setOrderType(OrderTypeEnum orderType) {
        super.setOrderType(orderType);
    }

    @JsonSetter("transactTime")
    @JsonDeserialize(using = EpochMilliToZonedDateTimeDeserializer.class)
    @Override
    public void setDate(ZonedDateTime date) {
        super.setDate(date);
    }

    @JsonSetter("executedQty")
    @Override
    public void setQuantity(CustomBigDecimal quantity) {
        super.setQuantity(quantity);
    }
}