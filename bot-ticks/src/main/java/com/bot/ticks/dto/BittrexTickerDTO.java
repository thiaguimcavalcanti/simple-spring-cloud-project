package com.bot.ticks.dto;

import lombok.Data;

@Data
public class BittrexTickerDTO {
    private double lastTradeRate;
    private double bidRate;
    private double askRate;
}
