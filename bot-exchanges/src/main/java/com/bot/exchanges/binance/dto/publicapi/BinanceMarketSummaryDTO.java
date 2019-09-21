package com.bot.exchanges.binance.dto.publicapi;

import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.binance.utils.BinanceUtils;
import com.fasterxml.jackson.annotation.JsonSetter;

import static com.bot.exchanges.binance.utils.BinanceUtils.splitSymbol;

public class BinanceMarketSummaryDTO extends MarketSummaryDTO {

    @Override
    public void setSymbol(String symbol) {
        super.setSymbol(symbol);
        String[] products = splitSymbol(symbol);
        super.setBaseProduct(products[0]);
        super.setProduct(products[1]);
    }

    @JsonSetter("priceChangePercent")
    @Override
    public void setChangePercent(Double changePercent) {
        super.setChangePercent(changePercent);
    }

    @JsonSetter("askPrice")
    @Override
    public void setAsk(CustomBigDecimal ask) {
        super.setAsk(ask);
    }

    @JsonSetter("bidPrice")
    @Override
    public void setBid(CustomBigDecimal bid) {
        super.setBid(bid);
    }

    @JsonSetter("highPrice")
    @Override
    public void setHigh(CustomBigDecimal high) {
        super.setHigh(high);
    }

    @JsonSetter("lastPrice")
    @Override
    public void setLast(CustomBigDecimal last) {
        super.setLast(last);
    }

    @JsonSetter("lowPrice")
    @Override
    public void setLow(CustomBigDecimal low) {
        super.setLow(low);
    }

    @JsonSetter("prevClosePrice")
    @Override
    public void setPrevDay(CustomBigDecimal prevDay) {
        super.setPrevDay(prevDay);
    }

    @JsonSetter("volume")
    @Override
    public void setVolume(CustomBigDecimal volume) {
        super.setVolume(volume);
    }
}
