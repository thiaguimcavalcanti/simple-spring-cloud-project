package com.bot.commons.dto;

import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class OrderHistoryDTO {
    private String id;
    private String symbol;
    private OrderTypeEnum orderType;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal quantity;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal price;
    private ZonedDateTime openedDate;
}
