package com.bot.exchanges.alphavantage.dto;

import com.bot.exchanges.alphavantage.utils.AlphaVantageDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.bot.commons.utils.DateUtils.DATETIME_FORMAT_DESERIALIZE;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = AlphaVantageDeserializer.class)
public class AlphaVantageStockTimeSeriesDTO extends BaseAlphaVantageDTO<AlphaVantageCandlestickDTO> {

    @Override
    public AlphaVantageCandlestickDTO newInstance(Map<String, String> properties) {
        return new AlphaVantageCandlestickDTO(properties);
    }

    @Override
    public DateTimeFormatter getDateTimeFormatter() {
        return DATETIME_FORMAT_DESERIALIZE;
    }
}
