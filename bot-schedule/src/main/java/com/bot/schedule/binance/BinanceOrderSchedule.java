package com.bot.schedule.binance;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.binance.utils.BinanceCondition;
import com.bot.schedule.commons.OrdersSchedule;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BinanceCondition.class)
public class BinanceOrderSchedule extends OrdersSchedule {

    @Autowired
    public BinanceOrderSchedule(OrderService orderService) {
        super(orderService);
        super.exchangeEnum = ExchangeEnum.BINANCE;
    }
}
