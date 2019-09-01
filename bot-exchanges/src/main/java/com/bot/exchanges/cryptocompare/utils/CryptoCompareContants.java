package com.bot.exchanges.cryptocompare.utils;

public class CryptoCompareContants {

    // General constants
    public static final String BASE_URL = "https://min-api.cryptocompare.com";
    public static final String FSYM = "fsym";
    public static final String TSYM = "tsym";
    public static final String LIMIT = "limit";
    public static final String EXCHANGE = "e";
    public static final String AGGREGATE = "aggregate";
    public static final String TRY_CONVERSION = "tryConversion";

    // Public APIs
    public static final String PUBLIC_API_COIN_LIST = "/data/all/coinlist";

    public static final String PUBLIC_API_HISTORICAL_MINUTE = "/data/histominute?fsym={" + FSYM + "}&tsym={" + TSYM +
            "}&limit={" + LIMIT + "}&e={" + EXCHANGE + "}&aggregate={" + AGGREGATE + "}&tryConversion={" + TRY_CONVERSION + "}";

    public static final String PUBLIC_API_HISTORICAL_HOUR = "/data/histohour?fsym={" + FSYM + "}&tsym={" + TSYM +
            "}&limit={" + LIMIT + "}&e={" + EXCHANGE + "}&aggregate={" + AGGREGATE + "}&tryConversion={" + TRY_CONVERSION + "}";
}
