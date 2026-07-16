package com.rithish.trading.loader;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

import com.rithish.trading.contracts.MarketDataLoader;

public class IndianApiHistoricalLoader implements MarketDataLoader {

    @Override
    public BarSeries loadSeries(String symbol) {

       return null;
    }
}