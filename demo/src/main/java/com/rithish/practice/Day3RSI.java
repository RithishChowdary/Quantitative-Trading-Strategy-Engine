package com.rithish.practice;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class Day3RSI {

    public static void main(String[] args) {

        BarSeries series = new BaseBarSeriesBuilder()
                .withName("Demo Series")
                .build();

        for (int i = 1; i <= 50; i++) {

            Bar bar = series.barBuilder()
                    .timePeriod(Duration.ofDays(1))
                    .endTime(Instant.now().plusSeconds(i))
                    .openPrice(100 + i)
                    .highPrice(100 + i)
                    .lowPrice(90 + i)
                    .closePrice(95 + i)
                    .volume(1000)
                    .build();

            series.addBar(bar);
        }

        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        RSIIndicator rsi =
                new RSIIndicator(closePrice, 14);

        int lastIndex = series.getEndIndex();

        System.out.println("Latest Close = "
                + closePrice.getValue(lastIndex));

        System.out.println("RSI(14) = "
                + rsi.getValue(lastIndex));
    }
}