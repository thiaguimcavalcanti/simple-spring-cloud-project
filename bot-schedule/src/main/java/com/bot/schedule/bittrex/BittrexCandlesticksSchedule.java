package com.bot.schedule.bittrex;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.bittrex.utils.BittrexCondition;
import com.bot.schedule.commons.CandlestickSchedule;
import com.bot.schedule.commons.service.CandlestickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(BittrexCondition.class)
public class BittrexCandlesticksSchedule extends CandlestickSchedule {

    @Autowired
    public BittrexCandlesticksSchedule(CandlestickService candlestickService) {
        super(candlestickService);
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }

    @Override
    public void refreshLatestCandlesticksFifteenMinsTask() {
        // Need to implement this period. Bittrex doesnt support this period.
    }
}
