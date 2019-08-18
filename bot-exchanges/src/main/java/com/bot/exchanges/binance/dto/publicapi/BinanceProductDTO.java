package com.bot.exchanges.binance.dto.publicapi;

import lombok.Data;

@Data
public class BinanceProductDTO {
    private String quoteAsset;
    private String baseAsset;
    private String status;
    private boolean active;

    public void setStatus(String status) {
        this.status = status;
        active = "TRADING".equals(status);
    }
}
