package com.bot.exchanges.commons.dto;

import com.bot.exchanges.commons.utils.MetricsUtils;
import lombok.Data;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

@Data
public class IndicatorRule {

    private final Indicator<Num> indicator1;
    private final Indicator<Num> indicator2;

    private OverIndicatorRule overIndicatorRule;
    private UnderIndicatorRule underIndicatorRule;
    private CrossedDownIndicatorRule crossedDownIndicatorRule;
    private CrossedUpIndicatorRule crossedUpIndicatorRule;

    public IndicatorRule(Indicator<Num> indicator1, Indicator<Num> indicator2) {
        this.indicator1 = indicator1;
        this.indicator2 = indicator2;
    }

    public Boolean isDifferenceWidening(int index) {
        return indicator2 != null ?  MetricsUtils.isDifferenceWidening(index, indicator1, indicator2) : null;
    }

    public boolean isRising(int index) {
        return MetricsUtils.isRising(index, indicator1);
    }

    public boolean isFalling(int index) {
        return MetricsUtils.isFalling(index, indicator1);
    }

    public boolean isAngleWidening(int index) {
        return MetricsUtils.isAngleWidening(index, indicator1);
    }
}
