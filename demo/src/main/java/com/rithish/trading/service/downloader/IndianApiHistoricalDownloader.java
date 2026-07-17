package com.rithish.trading.service.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rithish.trading.contracts.HistoricalDataDownloader;
import com.rithish.trading.dto.api.HistoricalResponse;
import com.rithish.trading.exceptions.HistoricalDataDownloadException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IndianApiHistoricalDownloader implements HistoricalDataDownloader {

    private static final String BASE_URL =
            "https://stock.indianapi.in/historical_data";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public IndianApiHistoricalDownloader() {

        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public HistoricalResponse download(String symbol,
                                       String period,
                                       String apiKey) {

        try {

            String url = BASE_URL
                    + "?stock_name=" + symbol
                    + "&period=" + period
                    + "&filter=price";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("x-api-key", apiKey)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request,
                            HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {

                throw new RuntimeException(
                        "Indian API Error : " + response.statusCode());
            }

            return objectMapper.readValue(
                    response.body(),
                    HistoricalResponse.class
            );

        } catch (IOException | InterruptedException e) {

            throw new HistoricalDataDownloadException("Failed to download historical data",e);
        }
    }
}