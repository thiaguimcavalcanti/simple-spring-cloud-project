package com.bot.schedule.commons;

import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.schedule.commons.session.helpers.ExchangeSessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.List;

@Component
@Transactional
public class GeneralSchedule {

    private ServletContext servletContext;

    private final ExchangesApiFacade exchangesApiFacade;

    private final ExchangeProductRepository exchangeProductRepository;

    @Autowired
    public GeneralSchedule(ExchangesApiFacade exchangesApiFacade,
                           ExchangeProductRepository exchangeProductRepository,
                           ServletContext servletContext) {
        this.exchangesApiFacade = exchangesApiFacade;
        this.exchangeProductRepository = exchangeProductRepository;
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void initialize() {
        ExchangeSessionHelper sessionHelper = ExchangeSessionHelper.getInstance(servletContext);

        List<ExchangeProduct> exchangeProducts = exchangeProductRepository.findByExchangeId(ExchangeEnum.BITTREX.getId());
        sessionHelper.initializeExchangeSession(ExchangeEnum.BITTREX, exchangeProducts);

        exchangeProducts = exchangeProductRepository.findByExchangeId(ExchangeEnum.BINANCE.getId());
        sessionHelper.initializeExchangeSession(ExchangeEnum.BINANCE, exchangeProducts);
    }

    @Async
    @Scheduled(cron = "0 0 * ? * *")
    public void refreshProductList() {
        exchangesApiFacade.refreshProductList();
    }

}
