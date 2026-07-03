package com.rithish.trading.report;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;

public class TradeReport {

    public void print(BarSeries series,
                      TradingRecord tradingRecord) {

        System.out.println("===== TRADE REPORT =====");

        System.out.println("Series Name : "
                + series.getName());

        System.out.println("Bars : "
                + series.getBarCount());

        System.out.println("Completed Trades : "
                + tradingRecord.getPositionCount());

        System.out.println("Current Position Open : "
                + tradingRecord.getCurrentPosition().isOpened());

    }
}