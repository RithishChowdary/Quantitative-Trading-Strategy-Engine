package com.rithish.trading.contracts;

import org.ta4j.core.BarSeries;

/**
 * MarketDataLoader
 */
public interface MarketDataLoader {
    BarSeries loadSeries(String symbol);
}
