package com.bot.commons.dto;

import com.bot.commons.types.CustomBigDecimal;
import lombok.Data;

@Data
public class TickerDTO {
    private String symbol;
    private String baseProduct;
    private String product;
    private Double changePercent;
    private CustomBigDecimal volume;
    private CustomBigDecimal high;
    private CustomBigDecimal low;
    private CustomBigDecimal last;
    private CustomBigDecimal ask;
    private CustomBigDecimal bid;
    private CustomBigDecimal prevDay;
}
