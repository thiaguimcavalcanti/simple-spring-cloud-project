package com.bot.schedule.binance;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.binance.utils.BinanceCondition;
import com.bot.schedule.commons.OrdersSchedule;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Conditional(BinanceCondition.class)
public class BinanceOrderSchedule extends OrdersSchedule {

    @Autowired
    public BinanceOrderSchedule(OrderService orderService) {
        super(orderService);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
