package com.bot.schedule.bittrex;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.bittrex.utils.BittrexCondition;
import com.bot.schedule.commons.CandlestickSchedule;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Conditional(BittrexCondition.class)
public class BittrexCandlesticksSchedule extends CandlestickSchedule {

    @Autowired
    public BittrexCandlesticksSchedule(CandlestickService candlestickService) {
        super(candlestickService);
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
