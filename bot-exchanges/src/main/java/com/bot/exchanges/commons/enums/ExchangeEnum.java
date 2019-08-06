package com.bot.exchanges.commons.enums;

import lombok.Getter;

@Getter
public enum ExchangeEnum {

    POLONIEX(1, "Poloniex"),
    BITTREX(2, "Bittrex"),
    BLEUTRADE(3, "Bleutrade"),
    BINANCE(4, "Binance");

    private int id;
    private String name;

    ExchangeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ExchangeEnum getByName(String name) {
        for (ExchangeEnum enumValue : values()) {
            if (enumValue.getName().equals(name)) {
                return enumValue;
            }
        }
        return null;
    }
}
