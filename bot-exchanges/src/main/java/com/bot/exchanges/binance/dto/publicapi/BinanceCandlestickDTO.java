package com.bot.exchanges.binance.dto.publicapi;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.commons.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceCandlestickDTO extends CandlestickDTO {

    @JsonCreator
    public BinanceCandlestickDTO(List<String> elements) {
        setBeginTime(DateUtils.convertEpochMilliToZonedDateTime(elements.get(0)));
        setOpenPrice(CustomBigDecimal.valueOf(elements.get(1)));
        setMaxPrice(CustomBigDecimal.valueOf(elements.get(2)));
        setMinPrice(CustomBigDecimal.valueOf(elements.get(3)));
        setClosePrice(CustomBigDecimal.valueOf(elements.get(4)));
        setVolume(CustomBigDecimal.valueOf(elements.get(5)));
        setEndTime(DateUtils.convertEpochMilliToZonedDateTime(elements.get(6)));
    }
}