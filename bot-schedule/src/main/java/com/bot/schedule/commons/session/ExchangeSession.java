package com.bot.schedule.commons.session;

import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExchangeSession {

    private ExchangeEnum exchangeEnum;

    private List<ExchangeProductDTO> exchangeProducts;

    private PeriodEnumMapSession<ExchangeProductDTO> exchangeProductsToRefreshLatestCandlestick = new PeriodEnumMapSession<>();

    public ExchangeSession(ExchangeEnum exchangeEnum) {
        this.exchangeEnum = exchangeEnum;
    }

    public void initialize(List<ExchangeProductDTO> exchangeProducts) {
        this.exchangeProducts = exchangeProducts;
        getExchangeProductsToRefreshLatestCandlestick().putAll(PeriodEnum.FIVE_MIN, exchangeProducts);
        getExchangeProductsToRefreshLatestCandlestick().putAll(PeriodEnum.FIFTEEN_MIN, exchangeProducts);
        getExchangeProductsToRefreshLatestCandlestick().putAll(PeriodEnum.ONE_HOUR, exchangeProducts);
    }
}
