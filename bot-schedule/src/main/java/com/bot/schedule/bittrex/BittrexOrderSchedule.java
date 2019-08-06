package com.bot.schedule.bittrex;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.OrdersSchedule;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BittrexOrderSchedule extends OrdersSchedule {

    public BittrexOrderSchedule() {
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
