package com.rithish.trading.contracts;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Strategy;

public interface StrategyFactory {

    Strategy create(BarSeries series);

}