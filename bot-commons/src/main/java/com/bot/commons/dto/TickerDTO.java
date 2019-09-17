package com.bot.commons.dto;

import com.bot.commons.types.CustomBigDecimal;
import lombok.Data;

@Data
public class TickerDTO {
    private String baseProduct;
    private String product;
    private CustomBigDecimal bid;
    private CustomBigDecimal ask;
    private CustomBigDecimal last;
}
