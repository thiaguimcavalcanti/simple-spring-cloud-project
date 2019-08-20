package com.bot.schedule.commons.service.impl;

import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.schedule.commons.service.CandlestickService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandlesticksServiceImpl implements CandlestickService {

    @Autowired
    private ExchangesApiFacade exchangesApiFacade;

    @Autowired
    private ExchangeProductRepository exchangeProductRepository;

    @Override
    public void refreshLatestTicks(ExchangeEnum exchangeEnum, PeriodEnum periodEnum) {

    }

    @Override
    public void refreshCandlesticks(ExchangeEnum exchangeEnum, PeriodEnum periodEnum) {
        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId((long) exchangeEnum.getId());
        if (CollectionUtils.isNotEmpty(exchangeProducts)) {
            exchangesApiFacade.refreshCandlesTicks(exchangeEnum, exchangeProducts.get(0), periodEnum);
        }
    }
}
