package com.bot.exchanges.binance.dto.publicapi;

import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.binance.enums.BinanceFilterTypeEnum;
import lombok.Data;

@Data
public class BinanceExchangeProductFilterDTO {
    private BinanceFilterTypeEnum filterType;
    private CustomBigDecimal minPrice;
    private CustomBigDecimal maxPrice;
    private CustomBigDecimal tickSize;
    private CustomBigDecimal multiplierUp;
    private CustomBigDecimal multiplierDown;
    private int avgPriceMins;
    private CustomBigDecimal minQty;
    private CustomBigDecimal maxQty;
    private CustomBigDecimal stepSize;
    private CustomBigDecimal minNotional;
}
