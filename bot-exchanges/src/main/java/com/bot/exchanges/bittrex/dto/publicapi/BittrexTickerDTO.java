package com.bot.exchanges.bittrex.dto.publicapi;

import com.bot.exchanges.commons.dto.TickerDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BittrexTickerDTO extends TickerDTO {

    @Override
    @JsonSetter("Bid")
    public void setBid(double bid) {
        super.setBid(bid);
    }

    @Override
    @JsonSetter("Ask")
    public void setAsk(double ask) {
        super.setAsk(ask);
    }

    @Override
    @JsonSetter("Last")
    public void setLast(double last) {
        super.setLast(last);
    }
}
