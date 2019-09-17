package com.bot.commons.dto;

import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class OrderHistoryDTO {
    private String id;
    private String symbol;
    private OrderTypeEnum orderType;
    private CustomBigDecimal quantity;
    private CustomBigDecimal price;
    private ZonedDateTime openedDate;
}
