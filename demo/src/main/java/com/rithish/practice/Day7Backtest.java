package com.rithish.practice;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.backtest.BarSeriesManager;

import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.averages.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;

public class Day7Backtest {

    public static void main(String[] args) {

        BarSeries series = new BaseBarSeriesBuilder()
                .withName("EMA + RSI Strategy")
                .build();

        // ----------------------------
        // Create Sample Market Data
        // ----------------------------
        for (int i = 1; i <= 100; i++) {

            double close;

            if (i < 30) {
                close = 200 - i;
            } else if (i < 60) {
                close = 170 + i;
            } else {
                close = 260 - i;
            }

            Bar bar = series.barBuilder()
                    .timePeriod(Duration.ofDays(1))
                    .endTime(Instant.now().plusSeconds(i))
                    .openPrice(close)
                    .highPrice(close + 5)
                    .lowPrice(close - 5)
                    .closePrice(close)
                    .volume(1000)
                    .build();

            series.addBar(bar);
        }

        // ----------------------------
        // Indicators
        // ----------------------------

        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        EMAIndicator ema9 =
                new EMAIndicator(closePrice, 9);

        EMAIndicator ema26 =
                new EMAIndicator(closePrice, 26);

        RSIIndicator rsi =
                new RSIIndicator(closePrice, 14);

        // ----------------------------
        // Rules
        // ----------------------------

        Rule emaBuy =
                new CrossedUpIndicatorRule(ema9, ema26);

        Rule emaSell =
                new CrossedDownIndicatorRule(ema9, ema26);

        Rule rsiBuy =
                new OverIndicatorRule(rsi, 45);

        Rule rsiSell =
                new UnderIndicatorRule(rsi, 55);

        Rule buyRule =
                emaBuy.and(rsiBuy);

        Rule sellRule =
                emaSell.or(rsiSell);

        // ----------------------------
        // Print Signals
        // ----------------------------

        System.out.println("===== SIGNALS =====");

        for (int i = 1; i <= series.getEndIndex(); i++) {

            if (buyRule.isSatisfied(i)) {

                System.out.println(
                        "BUY at Index = "
                                + i
                                + " | Close = "
                                + closePrice.getValue(i)
                                + " | RSI = "
                                + rsi.getValue(i));
            }

            if (sellRule.isSatisfied(i)) {

                System.out.println(
                        "SELL at Index = "
                                + i
                                + " | Close = "
                                + closePrice.getValue(i)
                                + " | RSI = "
                                + rsi.getValue(i));
            }
        }

        // ----------------------------
        // Run Backtest
        // ----------------------------

        Strategy strategy =
                new BaseStrategy(
                        buyRule,
                        sellRule);

        BarSeriesManager manager =
                new BarSeriesManager(series);

        TradingRecord tradingRecord =
                manager.run(strategy);

        // ----------------------------
        // Trade Details
        // ----------------------------

        System.out.println("\n===== TRADE DETAILS =====");

        double totalProfit = 0;

        for (var position : tradingRecord.getPositions()) {

            int entryIndex =
                    position.getEntry().getIndex();

            int exitIndex =
                    position.getExit().getIndex();

            double entryPrice =
                    series.getBar(entryIndex)
                            .getClosePrice()
                            .doubleValue();

            double exitPrice =
                    series.getBar(exitIndex)
                            .getClosePrice()
                            .doubleValue();

            double profit =
                    ((exitPrice - entryPrice)
                            / entryPrice) * 100;

            totalProfit += profit;

            System.out.println("Entry Index = " + entryIndex);
            System.out.println("Entry Price = " + entryPrice);

            System.out.println("Exit Index = " + exitIndex);
            System.out.println("Exit Price = " + exitPrice);

            System.out.println("Profit % = " + profit);

            System.out.println("--------------------------");
        }

        // ----------------------------
        // Latest Indicator Values
        // ----------------------------

        int endIndex = series.getEndIndex();

        System.out.println("\n===== LATEST VALUES =====");

        System.out.println("Latest Close = "
                + closePrice.getValue(endIndex));

        System.out.println("EMA9 = "
                + ema9.getValue(endIndex));

        System.out.println("EMA26 = "
                + ema26.getValue(endIndex));

        System.out.println("RSI = "
                + rsi.getValue(endIndex));

        // ----------------------------
        // Summary
        // ----------------------------

        System.out.println("\n===== BACKTEST =====");

        System.out.println("Completed Trades = "
                + tradingRecord.getPositionCount());

        System.out.println("Total Profit % = "
                + totalProfit);

        System.out.println("Current Position Open = "
                + tradingRecord.getCurrentPosition().isOpened());
    }
}