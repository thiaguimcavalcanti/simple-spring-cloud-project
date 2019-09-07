package com.bot.schedule.bovespa;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.bovespa.utils.BovespaCondition;
import com.bot.schedule.commons.StrategyAnalysisSchedule;
import com.bot.schedule.commons.client.StrategyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BovespaCondition.class)
public class BovespaStrategyAnalysisSchedule extends StrategyAnalysisSchedule {

    @Autowired
    public BovespaStrategyAnalysisSchedule(StrategyClient strategiesClient) {
        super(strategiesClient);
        super.exchangeEnum = ExchangeEnum.BOVESPA;
    }
}
