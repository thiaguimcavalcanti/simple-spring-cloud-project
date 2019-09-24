package com.bot.schedule.binance.websocket;

import com.bot.commons.dto.BaseListDTO;
import com.bot.schedule.binance.websocket.domain.BinanceCandlestick;
import com.bot.schedule.commons.client.CandlestickClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bot.commons.enums.ExchangeEnum.BINANCE;

abstract class BinanceKlineEvent extends BinanceCommonEvent {

    private static final Logger LOG = LogManager.getLogger(BinanceKlineEvent.class.getName());
    static final String TOPIC = "@kline";
    private static final String STREAM = "stream";
    private static final String STREAMS = "/" + STREAM + "?" + STREAM + "s=";
    Map<String, String> candlesticks;

    private CandlestickClient candlestickClient;
    private ObjectMapper mapper;

    public BinanceKlineEvent(String url,
                             ServletContext servletContext,
                             ObjectMapper mapper,
                             CandlestickClient candlestickClient) {
        super(url, servletContext);
        this.mapper = mapper;
        this.candlestickClient = candlestickClient;
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void refreshLatestCandlesticks() {
        candlestickClient.refreshLatestCandlesticks(BINANCE, BaseListDTO.of(convertCandlesticksToEntity(BinanceCandlestick.class)));
    }

    @Override
    URI generateUri(String url) {
        return URI.create(url + STREAMS + getStreamNames());
    }

    @Override
    public void onMessage(String message) {
        boolean isClosed = message.substring(message.indexOf("\"x\"") + 4, message.indexOf("\"x\"") + 10).contains("true");
        if (isClosed) {
            String symbol = message.substring(message.indexOf(STREAM) + 9, message.indexOf(TOPIC));
            String substring = message.substring(message.indexOf(TOPIC) + 7);
            String period = substring.substring(0, substring.indexOf("\""));
            candlesticks.put(symbol+ "_" + period, message);
        }
    }

    private <T> List<T> convertCandlesticksToEntity(Class<T> clazz) {
        List<T> candlesticksDTO = new ArrayList<>();
        new HashMap<>(candlesticks).forEach((key, value) -> {
            if (StringUtils.isNotBlank(value)) {
                try {
                    String data = mapper.readTree(value).get("data").get("k").toString();
                    candlesticksDTO.add(mapper.readValue(data, clazz));
                } catch (RuntimeException | IOException e) {
                    LOG.error(e);
                }
            }
            candlesticks.remove(key);
        });
        return candlesticksDTO;
    }

    abstract String getStreamNames();
}
