package com.bot.exchanges.commons.utils;

import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

public class MetricsUtils {

	public static Boolean isDifferenceWidening(int index, Indicator<Num> indicator1,
											   Indicator<Num> indicator2) {
		if (indicator2 == null) { return null; }

		Num currentValue1 = indicator1.getValue(index);
		Num currentValue2 = indicator2.getValue(index);

		Num previousValue1 = indicator1.getValue(index - 1);
		Num previousValue2 = indicator2.getValue(index - 1);

		Num currentCalculatedDifference = null;
		Num previousCalculatedDifference = null;

		if (currentValue1.isGreaterThan(currentValue2)) {
			currentCalculatedDifference = currentValue1.minus(currentValue2);
			previousCalculatedDifference = previousValue1.minus(previousValue2);
		} else {
			currentCalculatedDifference = currentValue2.minus(currentValue1);
			previousCalculatedDifference = previousValue2.minus(previousValue1);
		}

		return currentCalculatedDifference.compareTo(previousCalculatedDifference) > 0;
	}

	public static boolean isRising(int index, Indicator<Num> indicator) {
		Num currentValue = indicator.getValue(index);
		Num previousValue = indicator.getValue(index - 1);
		return currentValue.compareTo(previousValue) > 0;
	}

	public static boolean isFalling(int index, Indicator<Num> indicator) {
		Num currentValue = indicator.getValue(index);
		Num previousValue = indicator.getValue(index - 1);
		return currentValue.compareTo(previousValue) < 0;
	}

	public static boolean isAngleWidening(int index, Indicator<Num> indicator) {
		Num currentValue = indicator.getValue(index);
		Num previousValue = indicator.getValue(index - 1);
		Num secondPreviousValue = indicator.getValue(index - 2);

		Num previousCalculatedValue = previousValue.minus(secondPreviousValue);
		Num currentCalculatedValue = currentValue.minus(previousValue);

		return currentCalculatedValue.compareTo(previousCalculatedValue) >= 0;
	}
}
