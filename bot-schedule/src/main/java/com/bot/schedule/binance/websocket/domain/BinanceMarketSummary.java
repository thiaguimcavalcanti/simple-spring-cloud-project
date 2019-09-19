package com.bot.schedule.binance.websocket.domain;

import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BinanceMarketSummary extends MarketSummaryDTO {

    @JsonSetter("s")
    public void setSymbol_(String symbol) {
        super.setSymbol(symbol);
        super.setBaseProduct(symbol.substring(3));
        super.setProduct(symbol.substring(0, 3));
    }

    @JsonSetter("P")
    public void setChangePercent_(Double changePercent) {
        super.setChangePercent(changePercent);
    }

    @JsonSetter("a")
    public void setAsk_(CustomBigDecimal ask) {
        super.setAsk(ask);
    }

    @JsonSetter("b")
    public void setBid_(CustomBigDecimal bid) {
        super.setBid(bid);
    }

    @JsonSetter("h")
    public void setHigh_(CustomBigDecimal high) {
        super.setHigh(high);
    }

    @JsonSetter("c")
    public void setLast_(CustomBigDecimal last) {
        super.setLast(last);
    }

    @JsonSetter("l")
    public void setLow_(CustomBigDecimal low) { super.setLow(low); }

    @JsonSetter("v")
    public void setVolume_(CustomBigDecimal volume) {
        super.setVolume(volume);
    }
}
