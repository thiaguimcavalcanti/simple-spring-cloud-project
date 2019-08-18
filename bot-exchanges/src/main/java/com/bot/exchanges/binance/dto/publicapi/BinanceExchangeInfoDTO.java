package com.bot.exchanges.binance.dto.publicapi;

import lombok.Data;

import java.util.List;

@Data
public class BinanceExchangeInfoDTO {
    private List<BinanceProductDTO> symbols;
}
