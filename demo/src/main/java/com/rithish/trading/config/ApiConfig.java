package com.rithish.trading.config;

public class ApiConfig {

    private static final String API_KEY =
            "sk-live-sCjayOMqUtyfhqSLDBELe5icX7xRgrGAfaIHzpNb";

    private static final String BASE_URL =
            "https://stock.indianapi.in";

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

}