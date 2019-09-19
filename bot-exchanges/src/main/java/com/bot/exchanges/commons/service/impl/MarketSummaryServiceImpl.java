package com.bot.exchanges.commons.service.impl;

import com.bot.commons.dto.MarketSummaryDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.utils.DateUtils;
import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.MarketSummary;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.MarketSummaryRepository;
import com.bot.exchanges.commons.service.MarketSummaryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarketSummaryServiceImpl implements MarketSummaryService {

    private static final Logger LOG = LogManager.getLogger(MarketSummaryServiceImpl.class.getName());

    private ExchangeProductRepository exchangeProductRepository;
    private MarketSummaryRepository marketSummaryRepository;
    private ExchangesApiFacade exchangesApiFacade;
    private ModelMapper mapper;

    @Autowired
    public MarketSummaryServiceImpl(ExchangeProductRepository exchangeProductRepository,
                                    MarketSummaryRepository marketSummaryRepository,
                                    ExchangesApiFacade exchangesApiFacade,
                                    ModelMapper mapper) {
        this.exchangeProductRepository = exchangeProductRepository;
        this.marketSummaryRepository = marketSummaryRepository;
        this.exchangesApiFacade = exchangesApiFacade;
        this.mapper = mapper;
    }

    @Override
    public void refreshMarketSummaries(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct) {
        refreshMarketSummaries(exchangeEnum, exchangesApiFacade.getMarketSummaries(exchangeEnum, exchangeProduct));
    }

    @Override
    public void refreshMarketSummaries(ExchangeEnum exchangeEnum, List<? extends MarketSummaryDTO> marketSummariesDTO) {
        if (CollectionUtils.isNotEmpty(marketSummariesDTO)) {
            ZonedDateTime latestTickDate = DateUtils.roundZonedDateTime(ZonedDateTime.now(), Duration.ofMinutes(5));

            Map<String, ExchangeProduct> exchangeProducts = exchangeProductRepository
                    .findByExchangeId(exchangeEnum.getId()).stream()
                    .collect(Collectors.toMap(ep -> ep.getBaseProductId() + ep.getProductId(), ep -> ep));

            List<MarketSummary> marketSummaries = new ArrayList<>();
            marketSummariesDTO.forEach(marketSummaryDTO -> {
                ExchangeProduct ep = exchangeProducts.get(marketSummaryDTO.getBaseProduct() + marketSummaryDTO.getProduct());
                if (ep != null) {
                    MarketSummary marketSummary = mapper.map(marketSummaryDTO, MarketSummary.class);
                    marketSummary.setExchangeProduct(ep);
                    marketSummary.setTickDate(latestTickDate);
                    marketSummaries.add(marketSummary);
                }
            });

            marketSummaryRepository.saveAll(marketSummaries);
            marketSummaryRepository.flush();
        }
    }
}
