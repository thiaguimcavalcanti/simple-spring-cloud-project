package com.bot.schedule.binance;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import com.bot.schedule.commons.StrategyAnalysisSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BinanceStrategyAnalysisSchedule extends StrategyAnalysisSchedule {

    @Autowired
    public BinanceStrategyAnalysisSchedule(StrategyAnalysisService strategyAnalysisService,
                                           ExchangeProductRepository exchangeProductRepository) {
        super(strategyAnalysisService, exchangeProductRepository);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
