package com.bot.exchanges.bittrex.dto.publicapi;

import com.bot.commons.dto.TickerDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BittrexTickerDTO extends TickerDTO {

    @Override
    @JsonSetter("Bid")
    public void setBid(CustomBigDecimal bid) {
        super.setBid(bid);
    }

    @Override
    @JsonSetter("Ask")
    public void setAsk(CustomBigDecimal ask) {
        super.setAsk(ask);
    }

    @Override
    @JsonSetter("Last")
    public void setLast(CustomBigDecimal last) {
        super.setLast(last);
    }
}
