package com.bot.schedule.binance;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.binance.utils.BinanceCondition;
import com.bot.schedule.commons.StrategyAnalysisSchedule;
import com.bot.schedule.commons.client.StrategyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
@Conditional(BinanceCondition.class)
public class BinanceStrategyAnalysisSchedule extends StrategyAnalysisSchedule {

    @Autowired
    public BinanceStrategyAnalysisSchedule(StrategyClient strategiesClient,
                                           ServletContext servletContext) {
        super(strategiesClient, servletContext);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
