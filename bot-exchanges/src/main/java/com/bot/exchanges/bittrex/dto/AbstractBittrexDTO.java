package com.bot.exchanges.bittrex.dto;

import lombok.Data;

@Data
public class AbstractBittrexDTO<T> {
    private boolean success;
    private String message;
    private T result;
}
