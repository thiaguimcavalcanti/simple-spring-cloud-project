package com.bot.schedule.bovespa;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.bovespa.utils.BovespaCondition;
import com.bot.schedule.commons.CandlestickSchedule;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BovespaCondition.class)
public class BovespaCandlesticksSchedule extends CandlestickSchedule {

    @Autowired
    public BovespaCandlesticksSchedule(CandlestickService candlestickService) {
        super(candlestickService);
        super.exchangeEnum = ExchangeEnum.BOVESPA;
    }
}
