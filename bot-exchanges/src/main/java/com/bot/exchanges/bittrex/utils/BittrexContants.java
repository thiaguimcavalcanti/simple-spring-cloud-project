package com.bot.exchanges.bittrex.utils;

public class BittrexContants {

    // General constants
    public static final String USER_ID = "userId";
    public static final String API_SIGN = "apisign";
    public static final String API_KEY = "apikey";
    public static final String NONCE = "nonce";
    public static final String MARKET = "market";
    public static final String BASE_URL = "https://api.bittrex.com/api/v1.1";

    // Public APIs
    public static final String PUBLIC_API_TICKER = "/public/getticker?market={" + MARKET + "}";

    // Market APIs
    public static final String MARKET_API_GET_OPEN_ORDERS = "/market/getopenorders?market={" + MARKET + "}";

    // Account APIs
    public static final String ACCOUNT_API_GET_BALANCES = "/account/getbalances";
    public static final String ACCOUNT_API_GET_ORDER_HISTORY = "/account/getorderhistory?market={" + MARKET + "}";
}
