package com.bot.exchanges.binance.utils;

public class BinanceConstants {

    // General constants
    public static final String SYMBOL = "symbol";
    public static final String INTERVAL = "interval";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String LIMIT = "limit";
    public static final String BASE_URL = "https://api.binance.com";

    public static final String USER_ID = "userId";
    public static final String API_KEY = "X-MBX-APIKEY";
    public static final String SIGNATURE = "signature";

    // Public APIs
    public static final String PUBLIC_API_KLINES = "/api/v1/klines?symbol={" + SYMBOL + "}&interval={" + INTERVAL +
            "}&startTime={" + START_TIME + "}&endTime={" + END_TIME + "}&limit={" + LIMIT + "}";

}
