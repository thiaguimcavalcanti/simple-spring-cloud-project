package com.bot.schedule.binance;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.CandlestickSchedule;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BinanceCandlesticksSchedule extends CandlestickSchedule {

    @Autowired
    public BinanceCandlesticksSchedule(CandlestickService candlestickService) {
        super(candlestickService);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
