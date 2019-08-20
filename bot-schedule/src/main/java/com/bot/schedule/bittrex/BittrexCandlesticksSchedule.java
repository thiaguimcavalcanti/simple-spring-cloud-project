package com.bot.schedule.bittrex;

import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.CandlestickSchedule;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BittrexCandlesticksSchedule extends CandlestickSchedule {

    public BittrexCandlesticksSchedule() {
        super.exchangeEnum = ExchangeEnum.BITTREX;
    }
}
