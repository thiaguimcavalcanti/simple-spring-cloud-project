package com.bot.exchanges.commons.enums;

import static com.bot.exchanges.commons.utils.DateUtils.PARSE_BINANCE_DATE;
import static com.bot.exchanges.commons.utils.DateUtils.PARSE_BITTREX_DATE;

import lombok.Getter;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.function.BiFunction;

@Getter
public enum ExchangeEnum {

    POLONIEX((long) 1, "Poloniex", null),
    BITTREX((long) 2, "Bittrex", PARSE_BITTREX_DATE),
    BLEUTRADE((long) 3, "Bleutrade", null),
    BINANCE((long) 4, "Binance", PARSE_BINANCE_DATE);

    private Long id;
    private String name;
    private BiFunction<String, TemporalAmount, ZonedDateTime> parseDateFunction;

    ExchangeEnum(Long id, String name, BiFunction<String, TemporalAmount, ZonedDateTime> parseDateFunction) {
        this.id = id;
        this.name = name;
        this.parseDateFunction = parseDateFunction;
    }

    public static ExchangeEnum getByName(String name) {
        for (ExchangeEnum enumValue : values()) {
            if (enumValue.getName().equals(name)) {
                return enumValue;
            }
        }
        return null;
    }

    public BiFunction<String, TemporalAmount, ZonedDateTime> getParseDateFunction() {
        return parseDateFunction;
    }
}
