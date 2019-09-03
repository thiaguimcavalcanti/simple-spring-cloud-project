package com.bot.exchanges.commons.dto;

import com.bot.commons.enums.PeriodEnum;
import lombok.Data;

@Data
public class Period {
    private String oneMin;
    private String fiveMin;
    private String fifteenMin;
    private String thirdMin;
    private String oneHour;

    public String getByPeriodEnum(PeriodEnum periodEnum) {
        String period = null;
        switch (periodEnum) {
            case ONE_MIN:
                period = oneMin;
                break;
            case FIVE_MIN:
                period = fiveMin;
                break;
            case FIFTEEN_MIN:
                period = fifteenMin;
                break;
            case THIRTY_MIN:
                period = thirdMin;
                break;
            case ONE_HOUR:
                period = oneHour;
                break;
        }
        return period;
    }
}
