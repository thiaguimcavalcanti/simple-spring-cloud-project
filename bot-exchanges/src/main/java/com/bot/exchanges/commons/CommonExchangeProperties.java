package com.bot.exchanges.commons;

import com.bot.commons.enums.PeriodEnum;
import lombok.Data;

@Data
public class CommonExchangeProperties {
    private String baseUrl;
    private com.bot.exchanges.commons.dto.Period Period;

    public String getPeriodByPeriodEnum(PeriodEnum periodEnum) {
        return getPeriod().getByPeriodEnum(periodEnum);
    }
}
