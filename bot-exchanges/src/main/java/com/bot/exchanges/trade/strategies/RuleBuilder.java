package com.bot.exchanges.trade.strategies;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.StopGainRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RuleBuilder {

	private RuleBuilder() {

	}

	static Rule visitRule(JSONObject jsonRule, TimeSeries series, Map<String, Object> createdObjects)
			throws Exception {
		String key = jsonRule.toString();
		Rule rule = (Rule) createdObjects.get(key);
		if (rule != null) {
			return rule;
		}

		switch (jsonRule.getString("rule")) {
		case "OverIndicatorRule":
			rule = buildRule(OverIndicatorRule.class, jsonRule, series, createdObjects);
			break;
		case "UnderIndicatorRule":
			rule = buildRule(UnderIndicatorRule.class, jsonRule, series, createdObjects);
			break;
		case "CrossedDownIndicatorRule":
			rule = buildRule(CrossedDownIndicatorRule.class, jsonRule, series, createdObjects);
			break;
		case "CrossedUpIndicatorRule":
			rule = buildRule(CrossedUpIndicatorRule.class, jsonRule, series, createdObjects);
			break;
		case "StopGainRule":
			rule = buildStopGainRule(jsonRule, series, createdObjects);
			break;
		default:
			return null;
		}

		createdObjects.put(key, rule);

		return rule;
	}

	private static <T extends Rule> Rule buildRule(Class<T> clazz, JSONObject jsonRule, TimeSeries series,
			Map<String, Object> createdObjects) throws Exception {
		List<Class<?>> paramTypes = new ArrayList<>();
		List<Object> params = new ArrayList<>();

		JSONArray jsonIndicators = jsonRule.getJSONArray("indicators");
		for (int i = 0; i < jsonIndicators.length(); i++) {
			JSONObject jsonIndicator = jsonIndicators.getJSONObject(i);

			if (jsonIndicator.getString("type").equals("Number")) {
				paramTypes.add(Number.class);
				params.add(jsonIndicator.getDouble("value"));
			} else {
				paramTypes.add(Indicator.class);
				params.add(IndicatorBuilder.visitIndicator(jsonIndicator, series, createdObjects));
			}
		}

		Constructor<T> constructor = clazz.getConstructor(paramTypes.toArray(new Class[0]));
		return constructor.newInstance(params.toArray(new Object[0]));
	}

	@SuppressWarnings("rawtypes")
	private static Rule buildStopGainRule(JSONObject jsonRule, TimeSeries series, Map<String, Object> createdObjects) {
		List<Indicator> indicator = new ArrayList<>();
		Number threshold = 0;

		JSONArray jsonIndicators = jsonRule.getJSONArray("indicators");
		for (int i = 0; i < jsonIndicators.length(); i++) {
			JSONObject jsonIndicator = jsonIndicators.getJSONObject(i);

			if (jsonIndicator.getString("type").equals("Number")) {
				threshold = jsonIndicator.getDouble("value");
			} else {
				indicator.add(IndicatorBuilder.visitIndicator(jsonIndicator, series, createdObjects));
			}
		}

		return new StopGainRule((ClosePriceIndicator) indicator.get(0), threshold);
	}
}
