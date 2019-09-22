package com.bot.exchanges.backtesting;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

	private Map<String, ExchangeProduct> exchangeProducts;

	@Before
	public void initialize() {
		exchangeProducts = exchangeProductRepository.findByExchangeId(BINANCE.getId())
				.stream().collect(Collectors.toMap(c -> c.getBaseProductId() + c.getProductId(), c -> c));
	}

	public void performBTCETHBacktesting() {
		PeriodEnum fiveMin = PeriodEnum.FIVE_MIN;
		ExchangeProduct exchangeProduct = exchangeProducts.get("BTCETH");
		List<Candlestick> candlesticks = candlestickRepository.findByExchangeProductIdAndPeriodEnumOrderByBeginTimeAsc(
				exchangeProduct.getId(), fiveMin);

		candlesticks.forEach(candlestick -> strategyAnalysisService
				.analyzeStrategies(exchangeProduct, candlestick.getEndTime(), fiveMin));
	}
}
