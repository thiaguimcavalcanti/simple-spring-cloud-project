package com.bot.exchanges.alphavantage.client;

import com.bot.exchanges.alphavantage.AlphaVantageAuthenticationFeignConfiguration;
import com.bot.exchanges.alphavantage.dto.AlphaVantageStockTimeSeriesDTO;
import com.bot.exchanges.alphavantage.enums.FunctionTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.bot.exchanges.alphavantage.utils.AlphaVantageContants.FUNCTION;
import static com.bot.exchanges.alphavantage.utils.AlphaVantageContants.INTERVAL;
import static com.bot.exchanges.alphavantage.utils.AlphaVantageContants.SYMBOL;

@FeignClient(name = "alphaVantage-public", url = "${exchanges.alphavantage.baseUrl}",
        configuration = AlphaVantageAuthenticationFeignConfiguration.class)
public interface AlphaVantagePublicClient {

    @GetMapping(value = "${exchanges.alphavantage.apis.getTicks}")
    AlphaVantageStockTimeSeriesDTO getTicks(@PathVariable(SYMBOL) String symbol,
                                            @PathVariable(FUNCTION) FunctionTypeEnum function,
                                            @PathVariable(INTERVAL) String interval);
}
