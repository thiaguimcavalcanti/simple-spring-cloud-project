package com.bot.schedule.commons;

import com.bot.commons.dto.ExchangeProductDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.schedule.commons.client.ExchangeProductClient;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class GeneralSchedule {

    private ServletContext servletContext;

    private final ExchangeProductClient exchangeProductClient;

    @Autowired
    public GeneralSchedule(ExchangeProductClient exchangeProductClient,
                           ServletContext servletContext) {
        this.exchangeProductClient = exchangeProductClient;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void initialize() {
        ExchangeSessionHelper sessionHelper = ExchangeSessionHelper.getInstance(servletContext);

        List<ExchangeProductDTO> exchangeProducts = exchangeProductClient.findByExchangeId(ExchangeEnum.BITTREX);
        sessionHelper.initializeExchangeSession(ExchangeEnum.BITTREX, exchangeProducts);

        exchangeProducts = exchangeProductClient.findByExchangeId(ExchangeEnum.BINANCE);
        sessionHelper.initializeExchangeSession(ExchangeEnum.BINANCE, exchangeProducts);

        exchangeProducts = exchangeProductClient.findByExchangeId(ExchangeEnum.BOVESPA);
        sessionHelper.initializeExchangeSession(ExchangeEnum.BOVESPA, exchangeProducts);
    }

    @Async
    @Scheduled(cron = "0 */5 * ? * *")
    public void refreshProductList() {
        exchangeProductClient.refreshAll();
    }

}
