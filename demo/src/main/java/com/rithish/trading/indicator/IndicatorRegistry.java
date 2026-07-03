package com.rithish.trading.indicator;

import java.util.HashMap;
import java.util.Map;

import com.rithish.trading.contracts.IndicatorFactory;
import com.rithish.trading.model.IndicatorType;

public class IndicatorRegistry {

    private final Map<
            IndicatorType,
            IndicatorFactory> registry =
            new HashMap<>();

    public IndicatorRegistry() {

        registry.put(
                IndicatorType.EMA,
                new EMAFactory());

        registry.put(
                IndicatorType.RSI,
                new RSIFactory());

    }

    public IndicatorFactory getFactory(
            IndicatorType type) {

        IndicatorFactory factory =
                registry.get(type);

        if(factory == null){

            throw new IllegalArgumentException(
                    "Indicator not found : "
                            + type);
        }

        return factory;
    }

}