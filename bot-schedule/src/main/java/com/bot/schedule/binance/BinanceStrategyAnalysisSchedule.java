package com.bot.schedule.binance;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import com.bot.schedule.binance.utils.BinanceCondition;
import com.bot.schedule.commons.StrategyAnalysisSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;

@Component
@Transactional
@Conditional(BinanceCondition.class)
public class BinanceStrategyAnalysisSchedule extends StrategyAnalysisSchedule {

    @Autowired
    public BinanceStrategyAnalysisSchedule(StrategyAnalysisService strategyAnalysisService,
                                           ServletContext servletContext) {
        super(strategyAnalysisService, servletContext);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
