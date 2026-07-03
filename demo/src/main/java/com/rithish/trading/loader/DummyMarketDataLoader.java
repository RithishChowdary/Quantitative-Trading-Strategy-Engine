package com.rithish.trading.loader;

import java.time.Duration;
import java.time.Instant;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

import com.rithish.trading.contracts.MarketDataLoader;

public class DummyMarketDataLoader implements MarketDataLoader {

    @Override
    public BarSeries loadSeries() {

        BarSeries series =
                new BaseBarSeriesBuilder()
                        .withName("Dummy Market Data")
                        .build();

        for (int i = 1; i <= 100; i++) {

            double close;

            if (i < 30) {
                close = 200 - i;
            }
            else if (i < 60) {
                close = 170 + i;
            }
            else {
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

        return series;
    }
}