package com.bot.exchanges.commons.service.impl;

import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.utils.DateUtils;
import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.MarketSummary;
import com.bot.exchanges.commons.entities.Ticker;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.TickerRepository;
import com.bot.exchanges.commons.service.TickerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TickerServiceImpl implements TickerService {

    private static final Logger LOG = LogManager.getLogger(TickerServiceImpl.class.getName());

    private ExchangeProductRepository exchangeProductRepository;
    private TickerRepository tickerRepository;
    private ExchangesApiFacade exchangesApiFacade;
    private ModelMapper mapper;

    @Autowired
    public TickerServiceImpl(ExchangeProductRepository exchangeProductRepository,
                             TickerRepository tickerRepository,
                             ExchangesApiFacade exchangesApiFacade,
                             ModelMapper mapper) {
        this.exchangeProductRepository = exchangeProductRepository;
        this.tickerRepository = tickerRepository;
        this.exchangesApiFacade = exchangesApiFacade;
        this.mapper = mapper;
    }

    @Override
    public void refreshAll(ExchangeEnum exchangeEnum, List<? extends TickerDTO> tickersDTO) {
        if (CollectionUtils.isNotEmpty(tickersDTO)) {
            ZonedDateTime now = ZonedDateTime.now();

            List<Ticker> tickers = tickerRepository.findByExchangeId(exchangeEnum.getId());
            Map<String, Ticker> tickersBySymbol = tickers.stream().collect(Collectors.toMap(t ->
                    exchangeEnum.getId() + t.getExchangeProduct().getBaseProductId() + t.getExchangeProduct().getProductId(), t -> t));

            Map<String, ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(exchangeEnum.getId())
                    .stream().collect(Collectors.toMap(ep -> ep.getBaseProductId() + ep.getProductId(), ep -> ep));

            for (TickerDTO tickerDTO : tickersDTO) {
                Ticker ticker = tickersBySymbol.get(exchangeEnum.getId() + tickerDTO.getBaseProduct() + tickerDTO.getProduct());

                if (ticker != null) {
                    mapper.map(tickerDTO, ticker);
                    ticker.setLastUpdate(now);
                } else {
                    ExchangeProduct ep = exchangeProducts.get(tickerDTO.getBaseProduct() + tickerDTO.getProduct());
                    if (ep != null) {
                        ticker = mapper.map(tickerDTO, Ticker.class);
                        ticker.setExchangeProduct(ep);
                        ticker.setLastUpdate(now);
                        tickers.add(ticker);
                    }
                }
            }

            tickerRepository.saveAll(tickers);
            tickerRepository.flush();
        }
    }
}
