package com.bot.schedule.bittrex;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.bittrex.utils.BittrexCondition;
import com.bot.schedule.commons.MarketSummarySchedule;
import com.bot.schedule.commons.client.MarketSummaryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BittrexCondition.class)
public class BittrexMarketSummarySchedule extends MarketSummarySchedule {

    @Autowired
    public BittrexMarketSummarySchedule(MarketSummaryClient marketSummaryClient) {
        super(marketSummaryClient);
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
