package com.bot.schedule.binance;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.OrdersSchedule;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BinanceOrderSchedule extends OrdersSchedule {

    public BinanceOrderSchedule() {
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
