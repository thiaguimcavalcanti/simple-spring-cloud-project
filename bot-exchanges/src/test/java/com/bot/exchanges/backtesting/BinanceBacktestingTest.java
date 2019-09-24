package com.bot.exchanges.backtesting;

import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.backtesting.utils.StrategiesExecutor;
import com.bot.exchanges.commons.entities.BackTesting;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.repository.BackTestingRepository;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Order;
import org.ta4j.core.TradingRecord;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bot.commons.enums.ExchangeEnum.BINANCE;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BinanceBacktestingTest {

	@Autowired
	private ExchangeProductRepository exchangeProductRepository;

	@Autowired
	private CandlestickRepository candlestickRepository;
	
	@Autowired
	private StrategyAnalysisService strategyAnalysisService;

	@Autowired
	private BackTestingRepository backTestingRepository;

	private Map<String, ExchangeProduct> exchangeProducts;

	private EnumMap<PeriodEnum, Integer> qtyNextTicksToAnalyze = new EnumMap<>(PeriodEnum.class);
    {
        qtyNextTicksToAnalyze.put(PeriodEnum.FIVE_MIN, 60);
        qtyNextTicksToAnalyze.put(PeriodEnum.FIFTEEN_MIN, 20);
        qtyNextTicksToAnalyze.put(PeriodEnum.ONE_HOUR, 24);
    }

	@Before
	public void initialize() {
		exchangeProducts = exchangeProductRepository.findByExchangeId(BINANCE.getId())
				.stream().collect(Collectors.toMap(c -> c.getBaseProductId() + c.getProductId(), c -> c));
	}

	public void performBTCETHBackTesting() {
		PeriodEnum fiveMin = PeriodEnum.FIVE_MIN;
		ExchangeProduct exchangeProduct = exchangeProducts.get("BTCETH");
		List<Candlestick> candlesticks = candlestickRepository.findByExchangeProductIdAndPeriodEnumOrderByBeginTimeAsc(
				exchangeProduct.getId(), fiveMin);

		candlesticks.forEach(candlestick -> strategyAnalysisService
				.analyzeStrategies(exchangeProduct, candlestick.getEndTime(), fiveMin));
	}

	@Test
	public void performBackTesting() {
	    exchangeProducts.values().forEach(exchangeProduct -> {
	        if ((exchangeProduct.getBaseProductId() + exchangeProduct.getProductId()).equals("ETHDASH")) {
                PeriodEnum periodEnum = PeriodEnum.ONE_HOUR;
                List<Candlestick> candlesticks = candlestickRepository.findByExchangeProductIdAndPeriodEnumOrderByBeginTimeAsc(
                        exchangeProduct.getId(), periodEnum);
                if (!candlesticks.isEmpty()) {
                    try {
                        Map<Pair, BackTesting> backTestingMap = new HashMap<>();
                        //backTestingRepository.findAll().stream()
                        //.collect(Collectors.toMap(b -> Pair.of(b.getExchangeProductId(), b.getTickDate()), b -> b));

                        int TIME_SERIES_SIZE = 200;
                        List<Candlestick> start = candlesticks.subList(0, TIME_SERIES_SIZE);
                        Map<Candlestick, BackTesting> backingTestings = new HashMap<>();

                        // Generate the summary of the strategies
                        StrategiesExecutor strategiesExecutor = new StrategiesExecutor(start);
                        for (int i = TIME_SERIES_SIZE; i < candlesticks.size(); i++) {
                            Candlestick candlestick = candlesticks.get(i);
                            BackTesting backTesting = strategiesExecutor.execute(backTestingMap.get(
                                    Pair.of(candlestick.getExchangeProductId(), candlestick.getEndTime())), candlestick);
                            backingTestings.put(candlestick, backTestingRepository.save(backTesting));
                            System.out.println(candlestick.getBeginTime() + " - " + candlestick.getEndTime() + " : " + candlestick.getClosePrice());
                        }

                        // Calculate the profit
                        int beginIndex = 0;
                        for (int currentIndex = TIME_SERIES_SIZE; currentIndex < candlesticks.size(); currentIndex++) {
                            strategiesExecutor = new StrategiesExecutor(candlesticks.subList(beginIndex++, currentIndex));
                            Candlestick candlestickToBuy = candlesticks.get(currentIndex - 1);
                            TradingRecord tradingRecord = new BaseTradingRecord(Order.buyAt(strategiesExecutor.getEndIndex(),
                                    candlestickToBuy.getClosePrice(), CustomBigDecimal.valueOf(1)));
                            BackTesting backTesting = backingTestings.get(candlestickToBuy);

                            for (int nextIndex = currentIndex; nextIndex < currentIndex + qtyNextTicksToAnalyze.get(periodEnum); nextIndex++) {
                                if (nextIndex < candlesticks.size()) {
                                    Candlestick candlestick = candlesticks.get(nextIndex);
                                    boolean satisfied = strategiesExecutor.isStopGainSatisfied(candlestick, tradingRecord);
                                    CustomBigDecimal profit = calculateProfit(candlestickToBuy.getClosePrice(), candlestick.getClosePrice());

                                    if (satisfied && profit.compareTo(backTesting.getProfit()) > 0) {
                                        backTesting.setBuy(true);
                                        backTesting.setProfit(profit);
                                        backTestingRepository.save(backTesting);
                                        System.out.println(candlestick.getBeginTime() + " - " + candlestick.getEndTime() + " - Profit: " + profit);
                                    }
                                }
                            }
                        }
                    } catch (Throwable e) {
                        // ignore
                    }
                }
            }
        });
	}

	public CustomBigDecimal calculateProfit(CustomBigDecimal buyPrice, CustomBigDecimal sellPrice) {
		return sellPrice.minus(buyPrice).dividedBy(buyPrice).multipliedBy(CustomBigDecimal.valueOf(100));
	}
}
