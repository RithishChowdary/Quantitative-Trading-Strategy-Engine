package com.rithish.trading.loader;

import com.rithish.trading.contracts.MarketDataLoader;
import com.rithish.trading.dto.historical.HistoricalCandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;

public class CsvMarketDataLoader implements MarketDataLoader {

    @Override
    public BarSeries loadSeries(String symbol) {

        BarSeries series =
                new BaseBarSeriesBuilder()
                        .withName("Historical Data")
                        .build();

        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                                "historical/NSE/" + symbol + ".csv");

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(inputStream))) {

            // Skip CSV Header
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                HistoricalCandle candle =
                        new HistoricalCandle();

                candle.setDate(data[0]);

                candle.setOpen(
                        Double.parseDouble(data[1]));

                candle.setHigh(
                        Double.parseDouble(data[2]));

                candle.setLow(
                        Double.parseDouble(data[3]));

                candle.setClose(
                        Double.parseDouble(data[4]));

                candle.setVolume(
                        Long.parseLong(data[5]));

                // Next Step:
                // HistoricalCandle -> TA4J Bar

            }

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

        return series;

    }

}