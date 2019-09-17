package com.bot.exchanges.commons.service.impl;

import com.bot.commons.dto.OrderDTO;
import com.bot.commons.dto.TickerDTO;
import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.OrderStatusEnum;
import com.bot.commons.enums.OrderTypeEnum;
import com.bot.commons.exceptions.AppException;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.ExchangesApiFacade;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.OrderHistory;
import com.bot.exchanges.commons.entities.Strategy;
import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.repository.OrderHistoryRepository;
import com.bot.exchanges.commons.repository.StrategyRepository;
import com.bot.exchanges.commons.repository.StrategyRuleRepository;
import com.bot.exchanges.commons.service.OrderHistoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.num.Num;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bot.commons.enums.OrderStatusEnum.CANCELED;
import static com.bot.commons.enums.OrderStatusEnum.FILLED;
import static com.bot.commons.enums.OrderStatusEnum.NEW;
import static com.bot.commons.enums.OrderStatusEnum.READY_TO_START;
import static com.bot.commons.enums.OrderTypeEnum.BUY;
import static com.bot.commons.enums.OrderTypeEnum.SELL;
import static java.util.stream.Collectors.toMap;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private static final Logger LOG = LogManager.getLogger(OrderHistoryServiceImpl.class.getName());
    public static final Num MIN_VALUE = CustomBigDecimal.valueOf("0.00000001");

    private final OrderHistoryRepository orderHistoryRepository;
    private StrategyRepository strategyRepository;
    private StrategyRuleRepository strategyRuleRepository;
    private ExchangesApiFacade exchangesApiFacade;

    @Autowired
    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepository,
                                   StrategyRepository strategyRepository,
                                   StrategyRuleRepository strategyRuleRepository,
                                   ExchangesApiFacade exchangesApiFacade) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.strategyRepository = strategyRepository;
        this.strategyRuleRepository = strategyRuleRepository;
        this.exchangesApiFacade = exchangesApiFacade;
    }

    private Optional<OrderHistory> save(ExchangeProduct exchangeProduct, UserExchange userExchange,
                                        OrderTypeEnum orderTypeEnum, ZonedDateTime closeTime,
                                        CustomBigDecimal price, CustomBigDecimal profit) {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setQuantity(CustomBigDecimal.valueOf(100)); // get this value by user
        orderHistory.setOriginalDate(ZonedDateTime.now().withSecond(0).withNano(0));
        orderHistory.setDate(closeTime);
        orderHistory.setOriginalPrice(price); // get the current value
        orderHistory.setPrice(price); // get the current value
        orderHistory.setExchangeProduct(exchangeProduct);
        orderHistory.setSimulation(Boolean.TRUE);
        orderHistory.setStatus(READY_TO_START);
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

            boolean closed = latestOrderHistory != null ? OrderStatusEnum.isClosed(latestOrderHistory.getStatus()) : false;
            if (buySatisfied && (latestOrderHistory == null || (SELL.equals(latestOrderHistory.getType()) && closed))) {
                return save(exchangeProduct, userExchange, BUY, closeTime, closePrice, null);
            } else if (sellSatisfied && (latestOrderHistory != null && BUY.equals(latestOrderHistory.getType()) && closed)) {
                CustomBigDecimal profit = (CustomBigDecimal) closePrice.minus(latestOrderHistory.getPrice())
                        .dividedBy(latestOrderHistory.getPrice()).multipliedBy(CustomBigDecimal.valueOf(100));
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
        orderHistory.setDate(ZonedDateTime.now().withSecond(0).withNano(0));
        orderHistory.setStatus(FILLED);
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

    @Override
    public void executeAll(ExchangeEnum exchangeEnum) {
        List<OrderHistory> openedOrders = orderHistoryRepository
                .findByExchangeIdAndStatusIn(exchangeEnum.getId(), Arrays.asList(READY_TO_START, NEW));

        for (OrderHistory orderHistory : openedOrders) {
            try {
                boolean canExecuteOrder = validateBeforeCreateNewOrder(orderHistory);

                if (NEW.equals(orderHistory.getStatus()) || !canExecuteOrder) {
                    cancelOrder(orderHistory);
                }

                if (canExecuteOrder) {
                    TickerDTO tickerDTO = exchangesApiFacade.getTicker(exchangeEnum, orderHistory.getExchangeProduct());

                    if (BUY.equals(orderHistory.getType())) {
                        orderHistory.setPrice(tickerDTO.getBid().plus(MIN_VALUE));
                    } else {
                        orderHistory.setPrice(tickerDTO.getBid().minus(MIN_VALUE));
                    }

                    OrderDTO orderDTO = exchangesApiFacade.createNewOrder(orderHistory);

                    if (orderDTO != null && StringUtils.isNotBlank(orderDTO.getId())) {
                        orderHistory.setOrderId(orderDTO.getId());
                        fillAndSave(orderHistory, orderDTO);

                        if (FILLED.equals(orderHistory.getStatus())) {
                            confirmBuySellExecutedWithSuccess(orderHistory);
                        }
                    } else {
                        ExchangeProduct exchangeProduct = orderHistory.getExchangeProduct();
                        throw new AppException("ERROR TO CREATE A NEW ORDER: " + +exchangeProduct.getExchangeId() +
                                " - " + exchangeProduct.getBaseProductId() + exchangeProduct.getProductId());
                    }
                }
            } catch (AppException e) {
                LOG.error(e);
            }
        }
    }

    @Override
    public void monitoringOpenOrders(ExchangeEnum exchangeEnum) {
        List<OrderHistory> openedOrders = orderHistoryRepository.findByExchangeIdAndStatusIn(exchangeEnum.getId(),
                Arrays.asList(NEW));

        Map<String, List<OrderHistory>> ordersByUserId = openedOrders.stream()
                .collect(Collectors.groupingBy(o -> o.getUserExchange().getUserId()));

        ordersByUserId.forEach((userId, orders) -> {
            Map<String, OrderHistory> orderHistoryMap = orders.stream().collect(toMap(OrderHistory::getOrderId, o -> o));

            // Remove all orders that continue with the status "opened"
            exchangesApiFacade.getOpenOrders(exchangeEnum, null, userId)
                    .forEach(openOrder -> orderHistoryMap.remove(openOrder.getId()));

            // Update the status from the orders that were changed
            if (!orderHistoryMap.isEmpty()) {
                orderHistoryMap.forEach((orderId, orderHistory) -> {
                    List<? extends OrderDTO> allOrders = exchangesApiFacade.getAllOrders(exchangeEnum,
                            orderHistory.getExchangeProduct(), userId);

                    for (OrderDTO orderDTO : allOrders) {
                        if (orderHistory.getOrderId().equals(orderDTO.getId())) {
                            fillAndSave(orderHistory, orderDTO);
                            break;
                        }
                    }
                });
            }
        });
    }

    private boolean validateBeforeCreateNewOrder(OrderHistory orderHistory) {
        boolean acceptedDate = orderHistory.getDate().isAfter(ZonedDateTime.now().minusMinutes(30));
        return acceptedDate;
    }

    private boolean cancelOrder(OrderHistory orderHistory) {
        if (StringUtils.isNotBlank(orderHistory.getOrderId())) {
            OrderDTO orderDTO = exchangesApiFacade.cancelOrder(orderHistory.getUserExchange().getUserId(),
                    orderHistory.getExchangeProduct(), orderHistory.getOrderId());

            if (CANCELED.equals(orderDTO.getStatus())) {
                ExchangeProduct exchangeProduct = orderHistory.getExchangeProduct();
                LOG.warn("ORDER CANCELED WITH SUCCESS: " + exchangeProduct.getExchangeId() + " - " +
                        exchangeProduct.getBaseProductId() + exchangeProduct.getProductId());
                return true;
            } else {
                throw new AppException("ERROR TO CANCEL THE ORDER: " + orderHistory.getOrderId());
            }
        }
        return false;
    }

    private void fillAndSave(OrderHistory orderHistory, OrderDTO orderDTO) {
        orderHistory.setStatus(orderDTO.getStatus());
        orderHistory.setPrice(orderDTO.getPrice());
        orderHistory.setQuantity(orderDTO.getQuantity());
        orderHistory.setDate(orderDTO.getDate());
        orderHistoryRepository.save(orderHistory);
    }
}
