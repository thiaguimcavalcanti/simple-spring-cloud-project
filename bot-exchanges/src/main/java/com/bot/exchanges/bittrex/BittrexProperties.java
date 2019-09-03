package com.bot.exchanges.bittrex;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.dto.Period;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("exchanges.bittrex")
public class BittrexProperties {
    private String baseUrl;
    private Period Period;

    public String getPeriodByPeriodEnum(PeriodEnum periodEnum) {
        return getPeriod().getByPeriodEnum(periodEnum);
    }
}
