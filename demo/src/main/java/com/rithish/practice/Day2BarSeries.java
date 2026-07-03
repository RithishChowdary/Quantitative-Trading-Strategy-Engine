package com.rithish.practice;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

public class Day2BarSeries {

    public static void main(String[] args) {

        BarSeries series = new BaseBarSeriesBuilder()
                .withName("Demo Series")
                .build();

        Bar bar = series.barBuilder()
                .timePeriod(Duration.ofDays(1))
                .endTime(Instant.now())
                .openPrice(100)
                .highPrice(110)
                .lowPrice(95)
                .closePrice(105)
                .volume(1000)
                .build();

        series.addBar(bar);

        System.out.println("Bar Count = " + series.getBarCount());

        System.out.println("Close Price = "
                + series.getBar(0).getClosePrice());
    }
}