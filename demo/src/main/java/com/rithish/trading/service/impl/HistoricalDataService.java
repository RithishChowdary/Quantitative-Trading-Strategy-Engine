package com.rithish.trading.service.impl;

import com.rithish.trading.contracts.HistoricalDataDownloader;
import com.rithish.trading.dto.api.HistoricalResponse;
import com.rithish.trading.service.downloader.IndianApiHistoricalDownloader;

public class HistoricalDataService {

    private final HistoricalDataDownloader downloader;

    public HistoricalDataService() {

        this.downloader = new IndianApiHistoricalDownloader();
    }

    public HistoricalResponse downloadHistoricalData(
            String symbol,
            String period,
            String apiKey) {

        return downloader.download(
                symbol,
                period,
                apiKey
        );
    }

}