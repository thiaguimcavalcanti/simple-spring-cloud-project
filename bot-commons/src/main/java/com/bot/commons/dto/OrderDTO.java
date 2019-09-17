package com.bot.commons.dto;

import com.bot.commons.enums.OrderStatusEnum;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class OrderDTO {
    private String id;
    private String baseProduct;
    private String product;
    private OrderStatusEnum status;
    private OrderTypeEnum orderType;
    private CustomBigDecimal quantity;
    private CustomBigDecimal price;
    private CustomBigDecimal pricePerUnit;
    private ZonedDateTime date;
}
