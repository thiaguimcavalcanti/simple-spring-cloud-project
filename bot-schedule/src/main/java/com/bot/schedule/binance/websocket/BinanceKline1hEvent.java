package com.bot.schedule.binance.websocket;

import com.bot.schedule.commons.client.CandlestickClient;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.bot.commons.enums.ExchangeEnum.BINANCE;

@DependsOn({"generalSchedule"})
@Component
public class BinanceKline1hEvent extends BinanceKlineEvent {

    @Autowired
    public BinanceKline1hEvent(@Value("${exchanges.binance.websocket.url}") String url,
                               ServletContext servletContext,
                               ObjectMapper mapper,
                               CandlestickClient candlestickClient) {
        super(url, servletContext, mapper, candlestickClient);
    }

    String getStreamNames() {
        candlesticks = new ConcurrentHashMap<>();
        List<String> streams = new ArrayList<>();

        ExchangeSessionHelper exchangeSessionHelper = ExchangeSessionHelper.getInstance(servletContext);
        exchangeSessionHelper.getExchangeProducts(BINANCE).forEach(ep -> {
            String symbol = (ep.getProductId() + ep.getBaseProductId()).toLowerCase() + TOPIC;
            streams.add(symbol + "_1h");
        });

        return String.join("/", streams);
    }
}
