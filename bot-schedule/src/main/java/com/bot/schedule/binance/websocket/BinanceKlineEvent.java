package com.bot.schedule.binance.websocket;

import com.bot.commons.dto.BaseListDTO;
import com.bot.schedule.binance.websocket.domain.BinanceCandlestick;
import com.bot.schedule.commons.client.CandlestickClient;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.bot.commons.enums.ExchangeEnum.BINANCE;

@DependsOn({"generalSchedule"})
@Component
public class BinanceKlineEvent extends BinanceCommonEvent {

    private static final Logger LOG = LogManager.getLogger(BinanceKlineEvent.class.getName());
    private static final String TOPIC = "@kline";
    private static final String STREAM = "stream";
    private static final String STREAMS = "/" + STREAM + "?" + STREAM + "s=";
    private Map<String, String> candlesticks;

    private CandlestickClient candlestickClient;
    private ObjectMapper mapper;

    @Autowired
    public BinanceKlineEvent(@Value("${exchanges.binance.websocket.url}") String url,
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

    private String getStreamNames() {
        candlesticks = new ConcurrentHashMap<>();
        ExchangeSessionHelper exchangeSessionHelper = ExchangeSessionHelper.getInstance(servletContext);
        List<String> streams = exchangeSessionHelper.getExchangeProducts(BINANCE).stream().map(ep -> {
                String symbol = (ep.getProductId() + ep.getBaseProductId()).toLowerCase();
                return symbol + TOPIC + "_5m";
            }).collect(Collectors.toList());
        return String.join("/", streams);
    }
}
