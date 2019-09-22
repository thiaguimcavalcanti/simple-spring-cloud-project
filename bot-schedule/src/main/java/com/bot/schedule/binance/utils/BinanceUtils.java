package com.bot.schedule.binance.utils;

import com.bot.commons.enums.PeriodEnum;

import java.util.Map;

import static com.bot.commons.enums.PeriodEnum.FIFTEEN_MIN;
import static com.bot.commons.enums.PeriodEnum.FIVE_MIN;
import static com.bot.commons.enums.PeriodEnum.ONE_DAY;
import static com.bot.commons.enums.PeriodEnum.ONE_HOUR;
import static com.bot.commons.enums.PeriodEnum.THIRTY_MIN;

public class BinanceUtils {
    public static final String REGEX_SPLIT_SYMBOL = "(?<!^)((BTC)|(BNB)|(USDT)|(PAX)|(TUSD)|(USDC)|(BUSD)|(USDS)|(ETH)|(XRP)|(TRX))";

    public static final Map<String, PeriodEnum> PERIOD_ENUM_BY_STRING = Map.of(
            "5m", FIVE_MIN,
            "15m", FIFTEEN_MIN,
            "30m", THIRTY_MIN,
            "1h", ONE_HOUR,
            "1d", ONE_DAY);

    public static String[] splitSymbol(String symbol) {
        String product = symbol.split(REGEX_SPLIT_SYMBOL)[0];
        String baseProduct= symbol.substring(product.length());
        return new String[]{baseProduct, product};
    }
}
