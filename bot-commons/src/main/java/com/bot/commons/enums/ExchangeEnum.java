package com.bot.commons.enums;

import lombok.Getter;

@Getter
public enum ExchangeEnum {

    POLONIEX((long) 1, "Poloniex"),
    BITTREX((long) 2, "Bittrex"),
    BLEUTRADE((long) 3, "Bleutrade"),
    BINANCE((long) 4, "Binance"),
    BOVESPA((long) 5, "Bovespa");

    private Long id;
    private String name;

    ExchangeEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ExchangeEnum getById(Long id) {
        for (ExchangeEnum exchangeEnum : ExchangeEnum.values()) {
            if (exchangeEnum.getId().equals(id)) {
                return exchangeEnum;
            }
        }
        return null;
    }
}
