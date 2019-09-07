package com.bot.exchanges.alphavantage.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum AlphaVantageSymbolEnum {

    PETR4 ("PETR4.SAO");

    private String symbol;

    AlphaVantageSymbolEnum(String symbol) {
        this.symbol = symbol;
    }
}
