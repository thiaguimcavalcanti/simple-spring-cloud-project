package com.bot.commons.dto;

import com.bot.commons.enums.OrderStatusEnum;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class OrderDTO {
    private String id;
    private String baseProduct;
    private String product;
    private OrderStatusEnum status;
    private OrderTypeEnum orderType;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal quantity;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal price;
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private CustomBigDecimal pricePerUnit;
    private ZonedDateTime date;
}
