package com.bot.exchanges.trade.strategies;

import org.json.JSONException;
import org.json.JSONObject;
import org.ta4j.core.Indicator;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.indicators.helpers.MaxPriceIndicator;
import org.ta4j.core.indicators.helpers.MinPriceIndicator;
import org.ta4j.core.indicators.helpers.MultiplierIndicator;
import org.ta4j.core.num.Num;

import java.io.Serializable;
import java.util.Map;

class IndicatorBuilder {

	private static final String BAR_COUNT = "barCount";
	private static final String INDICATOR = "indicator";
	public static final String SHORT_BAR_COUNT = "shortBarCount";
	public static final String LONG_BAR_COUNT = "longBarCount";
	public static final String PRICE = "price";

	private IndicatorBuilder() {}

	static <T extends Serializable> Indicator<T> visitIndicator(JSONObject jsonIndicator, TimeSeries series,
																Map<String, Object> createdObjects) throws JSONException {
		String key = jsonIndicator.toString();

		Indicator<T> indicator = (Indicator<T>) createdObjects.get(key);
		if (indicator != null) {
			return indicator;
		}

		switch (jsonIndicator.getString("type")) {
		case "ClosePriceIndicator":
			indicator = (Indicator<T>) new ClosePriceIndicator(series);
			break;
		case "EMAIndicator":
			indicator = (Indicator<T>) buildEMAIndicator(jsonIndicator, series, createdObjects);
			break;
		case "StochasticOscillatorKIndicator":
			indicator = (Indicator<T>) buildStochasticOscillatorKIndicator(jsonIndicator, series);
			break;
		case "MACDIndicator":
			indicator = (Indicator<T>) buildMACDIndicator(jsonIndicator, series, createdObjects);
			break;
		case "RSIIndicator":
			indicator = (Indicator<T>) buildRSIIndicator(jsonIndicator, series, createdObjects);
			break;
		case "MaxPriceIndicator":
			indicator = (Indicator<T>) new MaxPriceIndicator(series);
			break;
		case "MinPriceIndicator":
			indicator = (Indicator<T>) new MinPriceIndicator(series);
			break;
		case "HighestValueIndicator":
			indicator = (Indicator<T>) buildHighestValueIndicator(jsonIndicator, series, createdObjects);
			break;
		case "LowestValueIndicator":
			indicator = (Indicator<T>) buildLowestValueIndicator(jsonIndicator, series, createdObjects);
			break;
		case "MultiplierIndicator":
			indicator = (Indicator<T>) buildMultiplierIndicator(jsonIndicator, series, createdObjects);
			break;
		default:
			return null;
		}

		createdObjects.put(key, indicator);

		return indicator;
	}

	private static MACDIndicator buildMACDIndicator(JSONObject jsonIndicator, TimeSeries series,
													Map<String, Object> createdObjects) throws JSONException {
		int shortBarCount = jsonIndicator.getInt(SHORT_BAR_COUNT);
		int longBarCount = jsonIndicator.getInt(LONG_BAR_COUNT);
		Indicator<Num> anotherIndicator = visitIndicator(jsonIndicator.getJSONObject(INDICATOR), series, createdObjects);
		return new MACDIndicator(anotherIndicator, shortBarCount, longBarCount);
	}

	private static StochasticOscillatorKIndicator buildStochasticOscillatorKIndicator(JSONObject jsonIndicator,
																					  TimeSeries series) throws JSONException {
		int barCount = jsonIndicator.getInt(BAR_COUNT);
		return new StochasticOscillatorKIndicator(series, barCount);
	}

	private static EMAIndicator buildEMAIndicator(JSONObject jsonIndicator, TimeSeries series,
												  Map<String, Object> createdObjects) throws JSONException {
		int barCount = jsonIndicator.getInt(BAR_COUNT);
		Indicator<Num> anotherIndicator = visitIndicator(jsonIndicator.getJSONObject(INDICATOR), series, createdObjects);
		return new EMAIndicator(anotherIndicator, barCount);
	}

	private static RSIIndicator buildRSIIndicator(JSONObject jsonIndicator, TimeSeries series,
												  Map<String, Object> createdObjects) throws JSONException {
		int barCount = jsonIndicator.getInt(BAR_COUNT);
		Indicator<Num> anotherIndicator = visitIndicator(jsonIndicator.getJSONObject(INDICATOR), series, createdObjects);
		return new RSIIndicator(anotherIndicator, barCount);
	}

	private static HighestValueIndicator buildHighestValueIndicator(JSONObject jsonIndicator, TimeSeries series,
																	Map<String, Object> createdObjects) throws JSONException {
		int barCount = jsonIndicator.getInt(BAR_COUNT);
		Indicator<Num> anotherIndicator = visitIndicator(jsonIndicator.getJSONObject(INDICATOR), series, createdObjects);
		return new HighestValueIndicator(anotherIndicator, barCount);
	}

	private static LowestValueIndicator buildLowestValueIndicator(JSONObject jsonIndicator, TimeSeries series,
																  Map<String, Object> createdObjects) throws JSONException {
		int barCount = jsonIndicator.getInt(BAR_COUNT);
		Indicator<Num> anotherIndicator = visitIndicator(jsonIndicator.getJSONObject(INDICATOR), series, createdObjects);
		return new LowestValueIndicator(anotherIndicator, barCount);
	}

	private static MultiplierIndicator buildMultiplierIndicator(JSONObject jsonIndicator, TimeSeries series,
																 Map<String, Object> createdObjects) throws JSONException {
		double price = jsonIndicator.getDouble(PRICE);
		Indicator<Num> anotherIndicator = visitIndicator(jsonIndicator.getJSONObject(INDICATOR), series, createdObjects);
		return new MultiplierIndicator(anotherIndicator, price);
	}
}
