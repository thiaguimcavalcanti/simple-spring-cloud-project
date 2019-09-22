package com.bot.exchanges.commons.utils;

import com.bot.commons.types.CustomBigDecimal;
import org.ta4j.core.num.Num;

public class OrderUtils {

    /**
     * Method to calculate the price/quantity using the tickSize/stepSize
     *
     * @see <a href="https://ninjatrader.com/support/forum/forum/ninjatrader-7/general-development/7811-tip-how-to-round-a-calculated-value-by-the-tick-size-of-an-instrument">Reference to calculate the value</a>
     * @param value Price/Quantity to execute the order
     * @param size Tick/Step size
     * @return The calculated value
     */
    public static CustomBigDecimal calculateValue(CustomBigDecimal value, CustomBigDecimal size) {
        CustomBigDecimal remainder = value.remainder(size);
        CustomBigDecimal sizeDividedByTwo = size.dividedBy(CustomBigDecimal.valueOf(2));
        CustomBigDecimal secondPart = remainder.compareTo(sizeDividedByTwo) < 0 ? CustomBigDecimal.valueOf(0) : size;
        return value.minus(remainder).plus(secondPart);
    }

    public static CustomBigDecimal verifyMinQty(CustomBigDecimal quantity, CustomBigDecimal minQty) {
        return quantity.compareTo(minQty) >= 0 ? quantity : minQty;
    }

    public static CustomBigDecimal calculateProfit(CustomBigDecimal buyPrice, CustomBigDecimal sellPrice) {
        return sellPrice.minus(buyPrice).dividedBy(buyPrice).multipliedBy(CustomBigDecimal.valueOf(100));
    }
}
