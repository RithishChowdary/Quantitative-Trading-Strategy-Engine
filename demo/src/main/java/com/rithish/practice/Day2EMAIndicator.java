package com.rithish.practice;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.indicators.averages.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class Day2EMAIndicator {

    public static void main(String[] args) {

        BarSeries series = new BaseBarSeriesBuilder()
                .withName("Demo Series")
                .build();

        for (int i = 1; i <= 50; i++) {

            Bar bar = series.barBuilder()
                    .timePeriod(Duration.ofDays(1))
                    .endTime(Instant.now().plusSeconds(i))
                    .openPrice(120 + i)
                    .highPrice(120 + i)
                    .lowPrice(110 + i)
                    .closePrice(110 + i)
                    .volume(1000)
                    .build();

            series.addBar(bar);
        }

        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        EMAIndicator ema20 =
                new EMAIndicator(closePrice, 20);

        EMAIndicator ema50 =
                new EMAIndicator(closePrice, 50);

        int lastIndex = series.getEndIndex();

        System.out.println("Latest Close = "
                + closePrice.getValue(lastIndex));

        System.out.println("EMA20 = "
                + ema20.getValue(lastIndex));

        System.out.println("EMA50 = "
                + ema50.getValue(lastIndex));
    }
}