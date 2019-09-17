package com.bot.exchanges.binance.dto.account;

import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.EpochMilliToZonedDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceOrderHistoryDTO extends OrderHistoryDTO {
    private boolean isBuyer;
    private boolean isMaker;

    @Override
    @JsonSetter("time")
    @JsonDeserialize(using = EpochMilliToZonedDateTimeDeserializer.class)
    public void setOpenedDate(ZonedDateTime openedDate) {
        super.setOpenedDate(openedDate);
    }

    @Override
    @JsonSetter("qty")
    public void setQuantity(CustomBigDecimal quantity) {
        super.setQuantity(quantity);
    }

    @Override
    public OrderTypeEnum getOrderType() {
        return isBuyer ? OrderTypeEnum.BUY : OrderTypeEnum.SELL;
    }
}