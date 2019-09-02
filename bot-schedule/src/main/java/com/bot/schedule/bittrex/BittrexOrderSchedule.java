package com.bot.schedule.bittrex;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.bittrex.utils.BittrexCondition;
import com.bot.schedule.commons.OrdersSchedule;
import com.bot.schedule.commons.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BittrexCondition.class)
public class BittrexOrderSchedule extends OrdersSchedule {

    @Autowired
    public BittrexOrderSchedule(OrderService orderService) {
        super(orderService);
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
