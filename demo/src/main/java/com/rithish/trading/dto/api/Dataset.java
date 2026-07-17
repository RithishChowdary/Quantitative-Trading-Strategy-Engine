package com.rithish.trading.dto.api;

import lombok.Data;

import java.util.List;

@Data
public class Dataset {

    private String metric;

    private String label;

    private List<List<Object>> values;

}