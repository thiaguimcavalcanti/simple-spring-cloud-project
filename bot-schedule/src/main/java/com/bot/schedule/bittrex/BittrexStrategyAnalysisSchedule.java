package com.bot.schedule.bittrex;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import com.bot.schedule.commons.StrategyAnalysisSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;

@Component
@Transactional
public class BittrexStrategyAnalysisSchedule extends StrategyAnalysisSchedule {

    @Autowired
    public BittrexStrategyAnalysisSchedule(StrategyAnalysisService strategyAnalysisService,
                                           ServletContext servletContext) {
        super(strategyAnalysisService, servletContext);
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
