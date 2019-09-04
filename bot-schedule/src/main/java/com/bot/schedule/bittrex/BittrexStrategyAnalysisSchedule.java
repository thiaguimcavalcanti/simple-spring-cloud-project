package com.bot.schedule.bittrex;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.bittrex.utils.BittrexCondition;
import com.bot.schedule.commons.StrategyAnalysisSchedule;
import com.bot.schedule.commons.client.StrategyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BittrexCondition.class)
public class BittrexStrategyAnalysisSchedule extends StrategyAnalysisSchedule {

    @Autowired
    public BittrexStrategyAnalysisSchedule(StrategyClient strategiesClient) {
        super(strategiesClient);
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
