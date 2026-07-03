package com.rithish.trading;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;

import com.rithish.trading.engine.BacktestEngine;
import com.rithish.trading.loader.DummyMarketDataLoader;
import com.rithish.trading.model.StrategyType;
import com.rithish.trading.report.TradeReport;
import com.rithish.trading.service.impl.StrategyService;
import com.rithish.trading.strategy.EmaRsiStrategyFactory;

@SuppressWarnings("unused")
public class App {
    public static void main(String[] args) {

        DummyMarketDataLoader loader = new DummyMarketDataLoader();

        BarSeries series = loader.loadSeries();

        Strategy strategy =new StrategyService()
                .getStrategy( StrategyType.EMA_RSI, series);

        TradingRecord tradingRecord =
                new BacktestEngine()
                        .run(series, strategy);

        new TradeReport()
                .print(series, tradingRecord);
    }
}
