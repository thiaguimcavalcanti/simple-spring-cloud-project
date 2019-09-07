package com.bot.exchanges.alphavantage.enums;

import com.bot.commons.enums.PeriodEnum;

import java.time.Duration;

import static com.bot.commons.enums.PeriodEnum.ONE_DAY;

public enum FunctionTypeEnum {
    TIME_SERIES_INTRADAY, TIME_SERIES_DAILY, TIME_SERIES_WEEKLY, TIME_SERIES_MONTHLY;

    public static FunctionTypeEnum getByPeriodEnum(PeriodEnum periodEnum) {
        if (periodEnum.getDuration() instanceof Duration) {
            return TIME_SERIES_INTRADAY;
        } else if (ONE_DAY.equals(periodEnum)) {
            return TIME_SERIES_DAILY;
        }
        return null;
    }
}
