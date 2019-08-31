package com.bot.schedule.commons;

import com.bot.exchanges.ExchangesApiFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GeneralSchedule {

    private final ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public GeneralSchedule(ExchangesApiFacade exchangesApiFacade) {
        this.exchangesApiFacade = exchangesApiFacade;
    }

    @Async
    @Scheduled(cron = "* * */1 * * ?")
    public void refreshProductList() {
        exchangesApiFacade.refreshProductList();
    }

}
