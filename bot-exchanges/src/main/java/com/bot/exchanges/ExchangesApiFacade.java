package com.bot.exchanges;

import com.bot.exchanges.bittrex.service.BittrexService;
import com.bot.exchanges.commons.dto.BalanceDTO;
import com.bot.exchanges.commons.dto.OpenOrderDTO;
import com.bot.exchanges.commons.dto.OrderHistoryDTO;
import com.bot.exchanges.commons.dto.TickerDTO;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangesApiFacade {

    @Autowired
    private BittrexService bittrexService;

    public TickerDTO getTicker(ExchangeEnum exchangeEnum, String market) {
        return getExchangeServiceByType(exchangeEnum).getTicker(market);
    }

    public List<? extends BalanceDTO> getBalances(ExchangeEnum exchangeEnum, String userId) {
        return getExchangeServiceByType(exchangeEnum).getBalances(userId);
    }

    List<? extends OpenOrderDTO> getOpenOrders(ExchangeEnum exchangeEnum, String userId, String market) {
        return getExchangeServiceByType(exchangeEnum).getOpenOrders(userId, market);
    }

    List<? extends OrderHistoryDTO> getOrderHistory(ExchangeEnum exchangeEnum, String userId, String market) {
        return getExchangeServiceByType(exchangeEnum).getOrderHistory(userId, market);
    }

    private ExchangeService getExchangeServiceByType(ExchangeEnum exchangeEnum) {
        switch (exchangeEnum) {
            case BITTREX:
                return bittrexService;
            default:
                return null;
        }
    }
}
