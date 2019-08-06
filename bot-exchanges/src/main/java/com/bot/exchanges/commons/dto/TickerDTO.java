package com.bot.exchanges.commons.dto;

import lombok.Data;

@Data
public class TickerDTO {
    private double bid;
    private double ask;
    private double last;
}
