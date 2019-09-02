package com.bot.commons.enums;

import static com.bot.commons.utils.DateUtils.PARSE_BINANCE_DATE;
import static com.bot.commons.utils.DateUtils.PARSE_BITTREX_DATE;

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
    private String cryptoCompareName;
    private BiFunction<String, TemporalAmount, ZonedDateTime> parseDateFunction;

    ExchangeEnum(Long id, String cryptoCompareName, BiFunction<String, TemporalAmount, ZonedDateTime> parseDateFunction) {
        this.id = id;
        this.cryptoCompareName = cryptoCompareName;
        this.parseDateFunction = parseDateFunction;
    }
}
