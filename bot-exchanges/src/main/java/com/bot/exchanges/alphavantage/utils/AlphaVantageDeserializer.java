package com.bot.exchanges.alphavantage.utils;

import com.bot.commons.utils.DateUtils;
import com.bot.exchanges.alphavantage.dto.AlphaVantageCandlestickDTO;
import com.bot.exchanges.alphavantage.dto.AlphaVantageStockTimeSeriesDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.bot.commons.utils.DateUtils.DATETIME_FORMAT_DESERIALIZE;

public class AlphaVantageDeserializer extends StdDeserializer<AlphaVantageStockTimeSeriesDTO> {

    public AlphaVantageDeserializer() {
        this(null);
    }

    public AlphaVantageDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public AlphaVantageStockTimeSeriesDTO deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectMapper oc = (ObjectMapper) jp.getCodec();
        JsonNode root = oc.readTree(jp);
        HashMap map = oc.readValue(root.toString(), HashMap.class);

        AlphaVantageStockTimeSeriesDTO dto = new AlphaVantageStockTimeSeriesDTO();

        String key = String.valueOf(map.keySet().toArray()[0]);
        Map<String, HashMap> candlesticks = (Map<String, HashMap>) map.get(key);
        candlesticks.forEach((time, candlestick) -> {
            AlphaVantageCandlestickDTO candlestickDTO = dto.newInstance(candlestick);
            candlestickDTO.setBeginTime(DateUtils.convertDateToZonedDateTime(time, dto.getDateTimeFormatter()));
            dto.addItem(candlestickDTO);
        });

        return dto;
    }
}
