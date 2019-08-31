package com.bot.schedule.bittrex;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.OrdersSchedule;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BittrexOrderSchedule extends OrdersSchedule {

    @Autowired
    public BittrexOrderSchedule(OrderService orderService) {
        super(orderService);
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
