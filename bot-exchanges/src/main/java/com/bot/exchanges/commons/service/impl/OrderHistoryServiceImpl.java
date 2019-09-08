package com.bot.exchanges.commons.service.impl;

import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.Strategy;
import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.repository.OrderHistoryRepository;
import com.bot.exchanges.commons.repository.StrategyRepository;
import com.bot.exchanges.commons.repository.StrategyRuleRepository;
import com.bot.exchanges.commons.service.OrderHistoryService;
import org.apache.commons.lang.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.num.Num;

import java.time.ZonedDateTime;
import java.util.Optional;

import static com.bot.commons.enums.OrderStatusEnum.CLOSED;
import static com.bot.commons.enums.OrderStatusEnum.NEW;
import static com.bot.commons.enums.OrderTypeEnum.BUY;
import static com.bot.commons.enums.OrderTypeEnum.SELL;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private static final Logger LOG = LogManager.getLogger(OrderHistoryServiceImpl.class.getName());

    private final OrderHistoryRepository orderHistoryRepository;
    private StrategyRepository strategyRepository;
    private StrategyRuleRepository strategyRuleRepository;

    @Autowired
    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepository,
                                   StrategyRepository strategyRepository,
                                   StrategyRuleRepository strategyRuleRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.strategyRepository = strategyRepository;
        this.strategyRuleRepository = strategyRuleRepository;
    }

    private Optional<OrderHistory> save(ExchangeProduct exchangeProduct, UserExchange userExchange,
                                        OrderTypeEnum orderTypeEnum, ZonedDateTime closeTime,
                                        CustomBigDecimal closePrice, CustomBigDecimal profit) {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setAmount(CustomBigDecimal.valueOf(1)); // get this value by user
        orderHistory.setOriginalDate(ZonedDateTime.now().withSecond(0).withNano(0));
        orderHistory.setDate(closeTime);
        orderHistory.setOriginalRate(closePrice); // get the current value
        orderHistory.setRate(closePrice); // get the current value
        orderHistory.setExchangeProduct(exchangeProduct);
        orderHistory.setSimulation(Boolean.TRUE);
        orderHistory.setStatus(NEW);
        orderHistory.setUserExchange(userExchange);
        orderHistory.setType(orderTypeEnum);
        orderHistory.setProfit(profit);

        return Optional.ofNullable(orderHistoryRepository.save(orderHistory));
    }

    @Override
    public Optional<OrderHistory> save(Boolean buySatisfied, Boolean sellSatisfied, ExchangeProduct exchangeProduct,
                                       UserExchange userExchange, ZonedDateTime closeTime, CustomBigDecimal closePrice) {
         buySatisfied = BooleanUtils.isTrue(buySatisfied);
         sellSatisfied = BooleanUtils.isTrue(sellSatisfied);

        if (buySatisfied || sellSatisfied) {
            OrderHistory latestOrderHistory = findTopByExchangeProductIdAndUserExchangeId(exchangeProduct.getId(),
                    userExchange.getId());

            if (buySatisfied && (latestOrderHistory == null
                    || (SELL.equals(latestOrderHistory.getType()) && CLOSED.equals(latestOrderHistory.getStatus())))) {
                return save(exchangeProduct, userExchange, BUY, closeTime, closePrice, null);
            } else if (sellSatisfied && (latestOrderHistory != null && BUY.equals(latestOrderHistory.getType())
                    && CLOSED.equals(latestOrderHistory.getStatus()))) {
                CustomBigDecimal profit = (CustomBigDecimal) closePrice.minus(latestOrderHistory.getRate())
                        .dividedBy(latestOrderHistory.getRate()).multipliedBy(CustomBigDecimal.valueOf(100));
                return save(exchangeProduct, userExchange, SELL, closeTime, closePrice, profit);
            } else {
                LOG.warn("New order try - ExchangeProductId: " + exchangeProduct.getId() +  ", Buy Satisfied: " +
                        buySatisfied + ", Sell Satisfied: " + sellSatisfied);
            }
        }
        return Optional.empty();
    }

    @Override
    public void confirmBuySellExecutedWithSuccess(OrderHistory orderHistory) {
        //orderHistory.setDate(ZonedDateTime.now().withSecond(0).withNano(0));
        orderHistory.setStatus(CLOSED);
        orderHistoryRepository.save(orderHistory);

        setStrategyActiveStatus(orderHistory.getExchangeProduct().getId(), orderHistory.getUserExchange().getId(),
                orderHistory.getType(),false);
    }

    private void setStrategyActiveStatus(Long exchangeProductId, Long userExchangeId, OrderTypeEnum orderTypeEnum,
                                         boolean active) {
        Strategy strategy = strategyRepository.findByExchangeProductIdAndUserExchangeId(exchangeProductId, userExchangeId);
        strategyRuleRepository.setBuyActiveByStrategyId(SELL.equals(orderTypeEnum), strategy.getId());
        strategyRuleRepository.setSellActiveByStrategyId(BUY.equals(orderTypeEnum), strategy.getId());
    }

    @Override
    public OrderHistory findTopByExchangeProductIdAndUserExchangeId(Long exchangeProductId, Long userExchangeId) {
        return orderHistoryRepository.findTopByExchangeProductIdAndUserExchangeIdOrderByDateDescTypeDesc(exchangeProductId,
                userExchangeId);
    }
}
