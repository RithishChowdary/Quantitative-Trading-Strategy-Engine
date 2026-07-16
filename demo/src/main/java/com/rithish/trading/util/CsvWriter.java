package com.rithish.trading.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.rithish.trading.dto.historical.HistoricalCandle;

public class CsvWriter {

    public void write(Path filePath,
                      List<HistoricalCandle> candles)
            throws IOException {

        try (BufferedWriter writer =
                     Files.newBufferedWriter(filePath)) {

            // Header
            writer.write("Date,Open,High,Low,Close,Volume");
            writer.newLine();

            // Data
            for (HistoricalCandle candle : candles) {

                writer.write(
                        candle.getDate() + "," +
                        candle.getOpen() + "," +
                        candle.getHigh() + "," +
                        candle.getLow() + "," +
                        candle.getClose() + "," +
                        candle.getVolume());

                writer.newLine();
            }
        }
    }

}