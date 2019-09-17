package com.bot.exchanges.binance.dto.publicapi;

import com.bot.commons.dto.TickerDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BinanceTickerDTO extends TickerDTO {

    @Override
    @JsonSetter("bidPrice")
    public void setBid(CustomBigDecimal bid) {
        super.setBid(bid);
    }

    @Override
    @JsonSetter("askPrice")
    public void setAsk(CustomBigDecimal ask) {
        super.setAsk(ask);
    }
}
