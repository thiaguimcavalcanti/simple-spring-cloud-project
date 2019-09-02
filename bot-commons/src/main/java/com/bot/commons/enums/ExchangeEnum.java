package com.bot.commons.enums;

import lombok.Getter;

@Getter
public enum ExchangeEnum {

    POLONIEX((long) 1, "Poloniex"),
    BITTREX((long) 2, "Bittrex"),
    BLEUTRADE((long) 3, "Bleutrade"),
    BINANCE((long) 4, "Binance");

    private Long id;
    private String cryptoCompareName;

    ExchangeEnum(Long id, String cryptoCompareName) {
        this.id = id;
        this.cryptoCompareName = cryptoCompareName;
    }
}
