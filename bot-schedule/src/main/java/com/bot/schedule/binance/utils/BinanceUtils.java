package com.bot.schedule.binance.utils;

public class BinanceUtils {
    public static final String REGEX_SPLIT_SYMBOL = "(?<!^)((BTC)|(BNB)|(USDT)|(PAX)|(TUSD)|(USDC)|(BUSD)|(USDS)|(ETH)|(XRP)|(TRX))";

    public static String[] splitSymbol(String symbol) {
        String product = symbol.split(REGEX_SPLIT_SYMBOL)[0];
        String baseProduct= symbol.substring(product.length());
        return new String[]{baseProduct, product};
    }
}
