package com.rithish.trading.engine;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.backtest.BarSeriesManager;

public class BacktestEngine {

    public TradingRecord run(BarSeries series,
                             Strategy strategy) {

        BarSeriesManager manager =
                new BarSeriesManager(series);

        return manager.run(strategy);
    }

}