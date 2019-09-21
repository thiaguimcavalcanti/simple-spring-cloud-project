package com.bot.schedule.binance.websocket;

import com.bot.commons.dto.BaseListDTO;
import com.bot.schedule.binance.websocket.domain.BinanceMarketSummary;
import com.bot.schedule.binance.websocket.domain.BinanceTicker;
import com.bot.schedule.commons.client.MarketSummaryClient;
import com.bot.schedule.commons.client.TickerClient;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.bot.commons.enums.ExchangeEnum.BINANCE;

@DependsOn({"generalSchedule"})
@Component
public class BinanceTickerEvent extends BinanceCommonEvent {

    private static final Logger LOG = LogManager.getLogger(BinanceTickerEvent.class.getName());
    private static final String STREAM = "stream";
    private static final String TOPIC = "@ticker";
    private static final String STREAMS = "/" + STREAM + "?" + STREAM + "s=";
    private Map<String, String> tickers;

    private TickerClient tickerClient;
    private MarketSummaryClient marketSummaryClient;
    private ObjectMapper mapper;

    @Autowired
    public BinanceTickerEvent(@Value("${exchanges.binance.websocket.url}") String url,
                              ServletContext servletContext,
                              MarketSummaryClient marketSummaryClient,
                              ObjectMapper mapper,
                              TickerClient tickerClient) {
        super(url, servletContext);
        this.marketSummaryClient = marketSummaryClient;
        this.mapper = mapper;
        this.tickerClient = tickerClient;
    }

    @Async
    @Scheduled(cron = "0 */5 * ? * *")
    public void refreshMarketSummaries() throws JsonProcessingException {
        marketSummaryClient.refreshAll(BINANCE, BaseListDTO.of(convertTickersToEntity(BinanceMarketSummary.class)));
    }

    @Async
    @Scheduled(cron = "* * * ? * *")
    public void refreshTickers() {
        tickerClient.refreshAll(BINANCE, BaseListDTO.of(convertTickersToEntity(BinanceTicker.class)));
    }

    @Override
    URI generateUri(String url) {
        return URI.create(url + STREAMS + getStreamNames());
    }

    @Override
    public void onMessage(String message) {
        String symbol = message.substring(message.indexOf(STREAM) + 9, message.indexOf(TOPIC));
        tickers.put(symbol, message);
    }

    private <T> List<T> convertTickersToEntity(Class<T> clazz) {
        List<T> tickersDTO = new ArrayList<>();
        tickers.values().forEach(ticker -> {
            if (StringUtils.isNotBlank(ticker)) {
                try {
                    String data = mapper.readTree(ticker).get("data").toString();
                    tickersDTO.add(mapper.readValue(data, clazz));
                } catch (RuntimeException | IOException e) {
                    LOG.error(e);
                }
            }
        });
        return tickersDTO;
    }

    private String getStreamNames() {
        tickers = new ConcurrentHashMap<>();
        ExchangeSessionHelper exchangeSessionHelper = ExchangeSessionHelper.getInstance(servletContext);
        List<String> streams = exchangeSessionHelper.getExchangeProducts(BINANCE).stream().map(ep -> {
                String symbol = (ep.getProductId() + ep.getBaseProductId()).toLowerCase();
                tickers.put(symbol, "");
                return symbol + TOPIC;
            }).collect(Collectors.toList());
        return String.join("/", streams);
    }
}
