package com.bot.schedule.binance;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.binance.utils.BinanceCondition;
import com.bot.schedule.commons.MarketSummarySchedule;
import com.bot.schedule.commons.client.MarketSummaryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BinanceCondition.class)
public class BinanceMarketSummarySchedule extends MarketSummarySchedule {

    @Autowired
    public BinanceMarketSummarySchedule(MarketSummaryClient marketSummaryClient) {
        super(marketSummaryClient);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
