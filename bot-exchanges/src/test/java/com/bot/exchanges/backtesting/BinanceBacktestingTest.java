package com.bot.exchanges.backtesting;

import com.bot.commons.enums.PeriodEnum;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.Exchange;
import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.entities.Strategy;
import com.bot.exchanges.commons.entities.StrategyRule;
import com.bot.exchanges.commons.entities.UserExchange;
import com.bot.exchanges.commons.repository.CandlestickRepository;
import com.bot.exchanges.commons.repository.ExchangeProductRepository;
import com.bot.exchanges.commons.repository.ExchangeRepository;
import com.bot.exchanges.commons.repository.StrategyRepository;
import com.bot.exchanges.commons.repository.StrategyRuleRepository;
import com.bot.exchanges.commons.repository.UserExchangeRepository;
import com.bot.exchanges.trade.service.StrategyAnalysisService;
import org.junit.Before;
import org.junit.Test;
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

	@Autowired
	private StrategyRepository strategyRepository;

	@Autowired
	private StrategyRuleRepository strategyRuleRepository;

	@Autowired
	private UserExchangeRepository userExchangeRepository;

	@Autowired
	private ExchangeRepository exchangeRepository;

	private Map<String, ExchangeProduct> exchangeProducts;

	@Before
	public void initialize() {
		exchangeProducts = exchangeProductRepository.findByExchangeId(BINANCE.getId())
				.stream().collect(Collectors.toMap(c -> c.getBaseProductId() + c.getProductId(), c -> c));
	}

	@Test
	public void performBTCETHBacktesting() {
		PeriodEnum fiveMin = PeriodEnum.FIVE_MIN;
		ExchangeProduct exchangeProduct = exchangeProducts.get("BTCETH");
		List<Candlestick> candlesticks = candlestickRepository.findByExchangeProductIdAndPeriodEnumOrderByBeginTimeAsc(
				exchangeProduct.getId(), fiveMin);

		candlesticks.forEach(candlestick -> strategyAnalysisService
				.analyzeStrategies(exchangeProduct, candlestick.getEndTime(), fiveMin));
	}

	@Test
	public void createStrategies() {
		String buyStrategy = "[\"AND\",{\"rule\":\"CrossedDownIndicatorRule\",\"indicators\":[{\"type\":\"RSIIndicator\",\"indicator\":{\"type\":\"ClosePriceIndicator\"},\"barCount\":14},{\"type\":\"Number\",\"value\":20}]}]";
		String sellStrategy = "[\"OR\",{\"rule\":\"CrossedUpIndicatorRule\",\"indicators\":[{\"type\":\"RSIIndicator\",\"indicator\":{\"type\":\"ClosePriceIndicator\"},\"barCount\":14},{\"type\":\"Number\",\"value\":70}]},{\"rule\":\"CrossedDownIndicatorRule\",\"indicators\":[{\"type\":\"MACDIndicator\",\"indicator\":{\"type\":\"ClosePriceIndicator\"},\"shortBarCount\":9,\"longBarCount\":16},{\"type\":\"EMAIndicator\",\"indicator\":{\"type\":\"MACDIndicator\",\"indicator\":{\"type\":\"ClosePriceIndicator\"},\"shortBarCount\":9,\"longBarCount\":16},\"barCount\":18}]}]";

		//strategyRuleRepository.deleteAll();
		//strategyRepository.deleteAll();

		Map<Long, UserExchange> userExchangeMap = userExchangeRepository.findAll().stream()
				.collect(Collectors.toMap(UserExchange::getExchangeId, u -> u));

		Map<Long, Exchange> exchangeMap = exchangeRepository.findAll().stream()
				.collect(Collectors.toMap(Exchange::getId, e -> e));

		exchangeProducts.values().forEach(ep -> {
				Strategy newStrategy = new Strategy();
				newStrategy.setActive(Boolean.TRUE);
				newStrategy.setExchangeProduct(ep);
				newStrategy.setUserExchange(userExchangeMap.get(ep.getExchangeId()));
				newStrategy.setName(exchangeMap.get(ep.getExchangeId()).getName() + " - " + ep.getBaseProductId() + "_" + ep.getProductId());
				strategyRepository.save(newStrategy);

				StrategyRule newStrategyRule = new StrategyRule();
				newStrategyRule.setBuyActive(Boolean.TRUE);
				newStrategyRule.setBuy(buyStrategy);
				newStrategyRule.setSellActive(Boolean.TRUE);
				newStrategyRule.setSell(sellStrategy);
				newStrategyRule.setPeriodEnum(PeriodEnum.FIVE_MIN);
				newStrategyRule.setStrategy(newStrategy);
				newStrategyRule.setStrategyId(newStrategy.getId());
				strategyRuleRepository.save(newStrategyRule);
		});
	}
}
