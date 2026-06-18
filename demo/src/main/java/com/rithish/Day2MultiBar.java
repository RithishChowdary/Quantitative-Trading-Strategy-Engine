package com.rithish;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

public class Day2MultiBar {

    public static void main(String[] args) {

        BarSeries series = new BaseBarSeriesBuilder()
                .withName("Demo Series")
                .build();

        for (int i = 1; i <= 50; i++) {

            Bar bar = series.barBuilder()
                    .timePeriod(Duration.ofDays(1))
                    .endTime(Instant.now().plusSeconds(i))
                    .openPrice(100 + i)
                    .highPrice(105 + i)
                    .lowPrice(95 + i)
                    .closePrice(100 + i)
                    .volume(1000)
                    .build();

            series.addBar(bar);
        }

        System.out.println("Total Bars = "
                + series.getBarCount());

        System.out.println("Last Close = "
                + series.getLastBar().getClosePrice());
    }
}