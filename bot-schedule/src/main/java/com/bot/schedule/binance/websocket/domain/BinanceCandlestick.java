package com.bot.schedule.binance.websocket.domain;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.EpochMilliToZonedDateTimeDeserializer;
import com.bot.commons.utils.RoundEpochMilliToZonedDateTimeCeilingDeserializer;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.ZonedDateTime;

import static com.bot.schedule.binance.utils.BinanceUtils.PERIOD_ENUM_BY_STRING;
import static com.bot.schedule.binance.utils.BinanceUtils.splitSymbol;

public class BinanceCandlestick extends CandlestickDTO {

    @JsonSetter("s")
    public void setSymbol_(String symbol) {
        super.setSymbol(symbol);
        String[] products = splitSymbol(symbol);
        super.setBaseProduct(products[0]);
        super.setProduct(products[1]);
    }

    @JsonSetter("t")
    @JsonDeserialize(using = EpochMilliToZonedDateTimeDeserializer.class)
    public void setBeginTime_(ZonedDateTime beginTime) {
        super.setBeginTime(beginTime);
    }

    @JsonSetter("v")
    public void setVolume_(CustomBigDecimal volume) {
        super.setVolume(volume);
    }

    @JsonSetter("c")
    public void setClosePrice_(CustomBigDecimal closePrice) {
        super.setClosePrice(closePrice);
    }

    @JsonSetter("T")
    @JsonDeserialize(using = RoundEpochMilliToZonedDateTimeCeilingDeserializer.class)
    public void setEndTime_(ZonedDateTime endTime) {
        super.setEndTime(endTime);
    }

    @JsonSetter("h")
    public void setMaxPrice_(CustomBigDecimal maxPrice) {
        super.setMaxPrice(maxPrice);
    }

    @JsonSetter("l")
    public void setMinPrice_(CustomBigDecimal minPrice) {
        super.setMinPrice(minPrice);
    }

    @JsonSetter("o")
    public void setOpenPrice_(CustomBigDecimal openPrice) {
        super.setOpenPrice(openPrice);
    }

    @JsonSetter("i")
    public void setPeriod_(String period) {
        super.setPeriodEnum(PERIOD_ENUM_BY_STRING.get(period));
    }
}
