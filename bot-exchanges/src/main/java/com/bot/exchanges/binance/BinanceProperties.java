package com.bot.exchanges.binance;

import com.bot.commons.enums.PeriodEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("exchanges.binance")
public class BinanceProperties {
    private String baseUrl;
    private com.bot.exchanges.commons.dto.Period Period;

    public String getPeriodByPeriodEnum(PeriodEnum periodEnum) {
        return getPeriod().getByPeriodEnum(periodEnum);
    }
}
