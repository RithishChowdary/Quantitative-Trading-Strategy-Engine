package com.rithish.practice;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.Rule;
import org.ta4j.core.indicators.averages.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;

public class Day5EMACrossRule {

    public static void main(String[] args) {

        BarSeries series = new BaseBarSeriesBuilder()
                .withName("EMA Cross")
                .build();

        for (int i = 1; i <= 60; i++) {

            double close;

            if (i < 30) {
                close = 150 - i;   // Downtrend
            } else {
                close = 120 + i;   // Uptrend
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

        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        EMAIndicator ema9 =
                new EMAIndicator(closePrice, 9);

        EMAIndicator ema26 =
                new EMAIndicator(closePrice, 26);

        Rule buyRule =
                new CrossedUpIndicatorRule(ema9, ema26);

        Rule sellRule =
                new CrossedDownIndicatorRule(ema9, ema26);

        System.out.println("===== EMA CROSS CHECK =====");

        for (int i = 1; i <= series.getEndIndex(); i++) {

            if (buyRule.isSatisfied(i)) {

                System.out.println(
                        "BUY SIGNAL at Index = " + i
                                + " | Close = "
                                + closePrice.getValue(i));
            }

            if (sellRule.isSatisfied(i)) {

                System.out.println(
                        "SELL SIGNAL at Index = " + i
                                + " | Close = "
                                + closePrice.getValue(i));
            }
        }

        int endIndex = series.getEndIndex();

        System.out.println("\n===== LATEST VALUES =====");

        System.out.println(
                "Latest Close = "
                        + closePrice.getValue(endIndex));

        System.out.println(
                "EMA9 = "
                        + ema9.getValue(endIndex));

        System.out.println(
                "EMA26 = "
                        + ema26.getValue(endIndex));
    }
}