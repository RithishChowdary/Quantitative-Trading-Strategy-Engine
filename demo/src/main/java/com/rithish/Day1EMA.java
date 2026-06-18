package com.rithish;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

public class Day1EMA {

    public static void main(String[] args) {

        BarSeries series = new BaseBarSeriesBuilder()
                .withName("Demo")
                .build();

        System.out.println(series.getName());
    }
}