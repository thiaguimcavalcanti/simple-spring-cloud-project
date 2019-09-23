package com.bot.exchanges.commons.dto;

import lombok.Data;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

@Data
public class IndicatorRule {
    private OverIndicatorRule overIndicatorRule;
    private UnderIndicatorRule underIndicatorRule;
    private CrossedDownIndicatorRule crossedDownIndicatorRule;
    private CrossedUpIndicatorRule crossedUpIndicatorRule;
}
