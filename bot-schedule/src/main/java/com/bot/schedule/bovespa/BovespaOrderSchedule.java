package com.bot.schedule.bovespa;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.bovespa.utils.BovespaCondition;
import com.bot.schedule.commons.OrdersSchedule;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BovespaCondition.class)
public class BovespaOrderSchedule extends OrdersSchedule {

    @Autowired
    public BovespaOrderSchedule(OrderService orderService) {
        super(orderService);
        super.exchangeEnum = ExchangeEnum.BOVESPA;
    }
}
