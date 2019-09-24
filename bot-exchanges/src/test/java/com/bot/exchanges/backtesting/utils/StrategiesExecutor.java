package com.bot.exchanges.backtesting.utils;

import com.bot.exchanges.commons.dto.IndicatorRule;
import com.bot.exchanges.commons.entities.BackTesting;
import com.bot.exchanges.commons.entities.Candlestick;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.StopGainRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StrategiesExecutor {

	private BaseTimeSeries timeSeries;
	private Map<Indicator, IndicatorRule> indicators = new HashMap<>();
	private StopGainRule stopGainRule;

	public StrategiesExecutor(List<Candlestick> candlesticks) {
		createRules(candlesticks);
	}

	public BackTesting execute(BackTesting backTesting, Candlestick candlestick) {
		timeSeries.addBar(candlestick);
		int endIndex = timeSeries.getEndIndex();

		if (backTesting == null) {
			backTesting = new BackTesting();
			backTesting.setExchangeProduct(candlestick.getExchangeProduct());
			backTesting.setPeriodEnum(candlestick.getPeriodEnum());
			backTesting.setTickDate(candlestick.getEndTime());
		}

		for (Entry<Indicator, IndicatorRule> entry : indicators.entrySet()) {
			Indicator indicator = entry.getKey();
			IndicatorRule indicatorRule = entry.getValue();
			if (indicator instanceof MACDIndicator) {
				backTesting.setMacdCrossedUpEma(indicatorRule.getCrossedUpIndicatorRule().isSatisfied(endIndex));
				backTesting.setMacdCrossedDownEma(indicatorRule.getCrossedDownIndicatorRule().isSatisfied(endIndex));
				backTesting.setMacdOverEma(indicatorRule.getOverIndicatorRule().isSatisfied(endIndex));
				backTesting.setMacdUnderEma(indicatorRule.getUnderIndicatorRule().isSatisfied(endIndex));
				backTesting.setMacdAngleWidening(indicatorRule.isAngleWidening(endIndex));
				backTesting.setMacdDifferenceWidening(indicatorRule.isDifferenceWidening(endIndex));
				backTesting.setMacdFalling(indicatorRule.isFalling(endIndex));
				backTesting.setMacdRising(indicatorRule.isRising(endIndex));
			} else if (indicator instanceof RSIIndicator) {
				backTesting.setRsiCrossedDown30(indicatorRule.getCrossedDownIndicatorRule().isSatisfied(endIndex));
				backTesting.setRsiCrossedUp70(indicatorRule.getCrossedUpIndicatorRule().isSatisfied(endIndex));
				backTesting.setRsiOver70(indicatorRule.getOverIndicatorRule().isSatisfied(endIndex));
				backTesting.setRsiUnder30(indicatorRule.getUnderIndicatorRule().isSatisfied(endIndex));
				backTesting.setRsiBetween3070(!backTesting.getRsiOver70() && !backTesting.getRsiUnder30());
				backTesting.setRsiAngleWidening(indicatorRule.isAngleWidening(endIndex));
				backTesting.setRsiDifferenceWidening(indicatorRule.isDifferenceWidening(endIndex));
				backTesting.setRsiFalling(indicatorRule.isFalling(endIndex));
				backTesting.setRsiRising(indicatorRule.isRising(endIndex));
			} else if (indicator instanceof CCIIndicator) {
				backTesting.setCciCrossedDownMinus100(indicatorRule.getCrossedDownIndicatorRule().isSatisfied(endIndex));
				backTesting.setCciCrossedUp100(indicatorRule.getCrossedUpIndicatorRule().isSatisfied(endIndex));
				backTesting.setCciOver100(indicatorRule.getOverIndicatorRule().isSatisfied(endIndex));
				backTesting.setCciUnderMinus100(indicatorRule.getUnderIndicatorRule().isSatisfied(endIndex));
				backTesting.setCciBetweenMinus100And100(!backTesting.getCciOver100() && !backTesting.getCciUnderMinus100());
				backTesting.setCciAngleWidening(indicatorRule.isAngleWidening(endIndex));
				backTesting.setCciDifferenceWidening(indicatorRule.isDifferenceWidening(endIndex));
				backTesting.setCciFalling(indicatorRule.isFalling(endIndex));
				backTesting.setCciRising(indicatorRule.isRising(endIndex));
			}
		}

		return backTesting;
	}

	public boolean isStopGainSatisfied(Candlestick candlestick, TradingRecord tradingRecord) {
		timeSeries.addBar(candlestick);
		return stopGainRule.isSatisfied(timeSeries.getEndIndex(), tradingRecord);
	}

	public int getEndIndex() {
		return timeSeries.getEndIndex();
	}

	private void createRules(List<Candlestick> candlesticks) {
		timeSeries = new BaseTimeSeries(new ArrayList<>(candlesticks));

		ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(timeSeries);

		RSIIndicator rsiIndicator = new RSIIndicator(closePriceIndicator, 14);
		indicators.put(rsiIndicator, createIndicatorRule(rsiIndicator, 30, 70));

		MACDIndicator macdIndicator = new MACDIndicator(closePriceIndicator, 12, 26);
		indicators.put(macdIndicator, createIndicatorRule(macdIndicator, new EMAIndicator(macdIndicator, 9)));

		CCIIndicator cciIndicator = new CCIIndicator(timeSeries, 20);
		indicators.put(cciIndicator, createIndicatorRule(cciIndicator, -100, 100));

		stopGainRule = new StopGainRule(closePriceIndicator, 1);
	}

	private IndicatorRule createIndicatorRule(Indicator<Num> firstIndicator, Number soldLine, Number boughtLine) {
		IndicatorRule indicatorRule = new IndicatorRule(firstIndicator, null);
		indicatorRule.setCrossedDownIndicatorRule(new CrossedDownIndicatorRule(firstIndicator, soldLine));
		indicatorRule.setCrossedUpIndicatorRule(new CrossedUpIndicatorRule(firstIndicator, boughtLine));
		indicatorRule.setOverIndicatorRule(new OverIndicatorRule(firstIndicator, boughtLine));
		indicatorRule.setUnderIndicatorRule(new UnderIndicatorRule(firstIndicator, soldLine));
		return indicatorRule;
	}

	private IndicatorRule createIndicatorRule(Indicator<Num> firstIndicator, Indicator<Num> secondIndicator) {
		IndicatorRule indicatorRule = new IndicatorRule(firstIndicator, secondIndicator);
		indicatorRule.setCrossedDownIndicatorRule(new CrossedDownIndicatorRule(firstIndicator, secondIndicator));
		indicatorRule.setCrossedUpIndicatorRule(new CrossedUpIndicatorRule(firstIndicator, secondIndicator));
		indicatorRule.setOverIndicatorRule(new OverIndicatorRule(firstIndicator, secondIndicator));
		indicatorRule.setUnderIndicatorRule(new UnderIndicatorRule(firstIndicator, secondIndicator));
		return indicatorRule;
	}
}
