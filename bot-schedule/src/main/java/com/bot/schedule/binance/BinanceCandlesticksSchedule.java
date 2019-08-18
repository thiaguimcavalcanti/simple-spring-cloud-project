package com.bot.schedule.binance;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.CandlestickSchedule;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BinanceCandlesticksSchedule extends CandlestickSchedule {

    public BinanceCandlesticksSchedule() {
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
