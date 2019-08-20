package com.bot.exchanges.bittrex.utils;

public class BittrexContants {

    // General constants
    public static final String USER_ID = "userId";
    public static final String API_SIGN = "apisign";
    public static final String API_KEY = "apikey";
    public static final String NONCE = "nonce";
    public static final String MARKET = "market";
    public static final String TICK_INTERVAL = "tickInterval";
    public static final String MARKET_NAME = "marketName";
    public static final String BASE_URL = "https://api.bittrex.com/api/v1.1";
    public static final String BASE_URL_2_0 = "https://bittrex.com/Api/v2.0";

    // Public APIs
    public static final String PUBLIC_API_TICKER = "/public/getticker?market={" + MARKET + "}";
    public static final String PUBLIC_API_GET_MARKETS = "/public/getmarkets";
    public static final String PUBLIC_API_GET_TICKS = "/pub/market/GetTicks?tickInterval={" + TICK_INTERVAL + "}&marketName={" + MARKET_NAME + "}";

    // Market APIs
    public static final String MARKET_API_GET_OPEN_ORDERS = "/market/getopenorders?market={" + MARKET + "}";

    // Account APIs
    public static final String ACCOUNT_API_GET_BALANCES = "/account/getbalances";
    public static final String ACCOUNT_API_GET_ORDER_HISTORY = "/account/getorderhistory?market={" + MARKET + "}";
}
