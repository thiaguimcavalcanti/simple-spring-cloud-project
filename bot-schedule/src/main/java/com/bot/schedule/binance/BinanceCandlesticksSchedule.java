package com.bot.schedule.binance;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.binance.utils.BinanceCondition;
import com.bot.schedule.commons.CandlestickSchedule;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BinanceCondition.class)
public class BinanceCandlesticksSchedule extends CandlestickSchedule {

    @Autowired
    public BinanceCandlesticksSchedule(CandlestickService candlestickService) {
        super(candlestickService);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
