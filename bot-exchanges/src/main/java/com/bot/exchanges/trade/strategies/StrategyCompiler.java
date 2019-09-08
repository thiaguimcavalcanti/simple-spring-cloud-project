package com.bot.exchanges.trade.strategies;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.trading.rules.BooleanRule;

import java.util.HashMap;
import java.util.Map;

public class StrategyCompiler {

	private static final String PAYLOAD = "[\"AND\",{\"rule\":\"StopGainRule\",\"indicators\":[{\"type\":\"ClosePriceIndicator\"},{\"type\":\"Number\",\"value\":1}]}]";

	/** Log */
	private static Logger logger = LogManager.getLogger(StrategyCompiler.class);

	public static Rule translateJsonToRule(String payload, TimeSeries series) {
		try {
			if (StringUtils.isNotBlank(payload)) {
				JSONArray array = new JSONArray(payload);
				Map<String, Object> createdObjects = new HashMap<>();
				return createFullRuleStructure(null, (String) array.remove(0), array, series, createdObjects);
			} else {
				return new BooleanRule(false);
			}
		} catch (Exception e) {
			logger.error("translateJsonToRule", e);
		}

		return null;
	}

	private static Rule createFullRuleStructure(Rule rule, String operator, JSONArray array, TimeSeries series,
			Map<String, Object> createdObjects) throws Exception {
		Object item = array.get(0);

		switch (operator) {
		case "AND":
			if (rule == null) {
				rule = BooleanRule.TRUE;
			}

			if (item instanceof JSONArray) {
				JSONArray subArray = (JSONArray) item;
				rule = rule.and(
						createFullRuleStructure(null, (String) subArray.remove(0), subArray, series, createdObjects));
			} else {
				rule = rule.and(RuleBuilder.visitRule((JSONObject) item, series, createdObjects));
			}
			break;
		case "OR":
			if (rule == null) {
				rule = BooleanRule.FALSE;
			}

			if (item instanceof JSONArray) {
				JSONArray subArray = (JSONArray) item;
				rule = rule.or(
						createFullRuleStructure(null, (String) subArray.remove(0), subArray, series, createdObjects));
			} else {
				rule = rule.or(RuleBuilder.visitRule((JSONObject) item, series, createdObjects));
			}
			break;
		default:
			rule = rule.and(BooleanRule.TRUE);
		}

		// Remove FIRST_ITEM used
		array.remove(0);

		if (array.length() > 0) {
			return createFullRuleStructure(rule, operator, array, series, createdObjects);
		}

		return rule;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		Rule toRule = StrategyCompiler.translateJsonToRule(PAYLOAD, new BaseTimeSeries());
		long stopTime = System.currentTimeMillis();
		System.out.println(stopTime - startTime);
		System.out.println(toRule);
	}
}
