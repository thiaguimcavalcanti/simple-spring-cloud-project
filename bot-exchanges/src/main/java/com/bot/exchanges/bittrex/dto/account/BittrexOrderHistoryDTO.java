package com.bot.exchanges.bittrex.dto.account;

import com.bot.commons.dto.OrderHistoryDTO;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class BittrexOrderHistoryDTO extends OrderHistoryDTO {

    @Override
    @JsonSetter("Uuid")
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    @JsonSetter("Exchange")
    public void setSymbol(String id) {
        super.setSymbol(id);
    }

    @Override
    @JsonSetter("TimeStamp")
    public void setOpenedDate(ZonedDateTime openedDate) {
        super.setOpenedDate(openedDate);
    }

    @JsonSetter("OrderType")
    public void setOrderType(String orderType) {
        super.setOrderType(OrderTypeEnum.valueOf(orderType.toUpperCase()));
    }

    @Override
    @JsonSetter("Limit")
    public void setPrice(CustomBigDecimal price) {
        super.setPrice(price);
    }

    @Override
    @JsonSetter("Quantity")
    public void setQuantity(CustomBigDecimal quantity) {
        super.setQuantity(quantity);
    }
}
