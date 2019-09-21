package com.bot.commons.dto;

import lombok.Data;
import org.ta4j.core.num.Num;

@Data
public class TickerTestDTO {
    private String symbol;
    private String baseProduct;
    private String product;
    private Double changePercent;
    private Num volume;
    private Num high;
    private Num low;
    private Num last;
    private Num ask;
    private Num bid;
    private Num prevDay;
}
