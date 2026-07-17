package com.rithish.trading.dto.api;

import lombok.Data;

import java.util.List;

@Data
public class HistoricalResponse {

    private List<Dataset> datasets;

}