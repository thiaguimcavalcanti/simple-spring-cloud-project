package com.bot.exchanges.commons.service.impl;

import com.bot.commons.dto.CandlestickDTO;
import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.service.CandlestickService;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandlestickServiceImpl implements CandlestickService {

    private static final Logger LOG = LogManager.getLogger(CandlestickServiceImpl.class.getName());

    private CandlestickRepository candlestickRepository;
    private StrategyAnalysisService strategyAnalysisService;
    private ExchangesApiFacade exchangesApiFacade;
    private ModelMapper mapper;

    @Autowired
    public CandlestickServiceImpl(CandlestickRepository candlestickRepository,
                                  StrategyAnalysisService strategyAnalysisService,
                                  ExchangesApiFacade exchangesApiFacade,
                                  ModelMapper mapper) {
        this.candlestickRepository = candlestickRepository;
        this.strategyAnalysisService = strategyAnalysisService;
        this.exchangesApiFacade = exchangesApiFacade;
        this.mapper = mapper;
    }

    @Override
    public void refreshLatestCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        LOG.warn("refreshLatestCandlestick - " + exchangeProduct.getExchangeId() + " - " + periodEnum + ": " +
                exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId());

        // Latest candlestick
        CandlestickDTO latestCandlestickDTO = exchangesApiFacade.getLatestCandlestick(exchangeProduct, periodEnum);
        Candlestick latestCandlestick = mapper.map(latestCandlestickDTO, Candlestick.class);
        fillAdditionalCandlestickInfo(exchangeProduct, periodEnum, latestCandlestick);

        // Previous candlestick to compare with the latest
        Candlestick previousCandlestick = candlestickRepository
                .findTopByExchangeProductIdAndPeriodEnumOrderByBeginTimeDesc(exchangeProduct.getId(), periodEnum);

        // Refresh all the candlesticks from this Exchange Product if the latestCandlestick is not the next expected tick
        if (latestCandlestick == null || previousCandlestick == null ||
                previousCandlestick.getEndTime().compareTo(latestCandlestick.getBeginTime()) < 0) {
            refreshCandlestick(exchangeProduct, periodEnum);
        }

        if (latestCandlestick != null) {
            candlestickRepository.save(latestCandlestick);

            // Perform the technical analysis (async task)
            if (previousCandlestick == null || previousCandlestick.getBeginTime().compareTo(latestCandlestick.getBeginTime()) < 0) {
                strategyAnalysisService.analyzeStrategies(exchangeProduct, latestCandlestick.getEndTime(), periodEnum);
            }
        }
    }

    @Override
    @Async
    public List<? extends CandlestickDTO> refreshCandlestick(ExchangeProduct exchangeProduct, PeriodEnum periodEnum) {
        LOG.warn("refreshCandlestick - "+ exchangeProduct.getExchangeId() + " - " + periodEnum + ": " +
                exchangeProduct.getBaseProductId() + "-" + exchangeProduct.getProductId());

        List<? extends CandlestickDTO> candlesticksDTO = exchangesApiFacade.getCandlesticks(exchangeProduct, periodEnum);

        if (CollectionUtils.isNotEmpty(candlesticksDTO)) {
            // Ignore the last one, because this candlestick is still opened
            candlesticksDTO.remove(candlesticksDTO.size() - 1);

            List<Candlestick> candlesticks = candlesticksDTO.stream().map(candlestickDTO -> {
                Candlestick candlestick = mapper.map(candlestickDTO, Candlestick.class);
                fillAdditionalCandlestickInfo(exchangeProduct, periodEnum, candlestick);
                return candlestick;
            }).collect(Collectors.toList());

            candlestickRepository.saveAll(candlesticks);
            candlestickRepository.flush();
        }

        return candlesticksDTO;
    }

    private void fillAdditionalCandlestickInfo(ExchangeProduct exchangeProduct, PeriodEnum periodEnum,
                                               Candlestick latestCandlestick) {
        if (latestCandlestick != null) {
            latestCandlestick.setExchangeProduct(exchangeProduct);
            latestCandlestick.setPeriodEnum(periodEnum);

            StringBuilder sb = new StringBuilder();
            latestCandlestick.setId(sb.append(latestCandlestick.getExchangeProduct().getId()).append("-")
                    .append(latestCandlestick.getPeriodEnum().getDuration().toString()).append("-")
                    .append(latestCandlestick.getBeginTime().toEpochSecond()).append("-")
                    .append(latestCandlestick.getEndTime().toEpochSecond()).toString().toUpperCase());
        }
    }
}