package com.rithish.trading.strategy;

import java.util.HashMap;
import java.util.Map;

import com.rithish.trading.contracts.StrategyFactory;
import com.rithish.trading.model.StrategyType;

public class StrategyRegistry {

    private final Map<StrategyType, StrategyFactory> registry =
            new HashMap<>();

    public StrategyRegistry() {

        registry.put(
                StrategyType.EMA_RSI,
                new EmaRsiStrategyFactory());

        // Future

        // registry.put(
        // StrategyType.EMA_MACD,
        // new EmaMacdStrategyFactory());

        // registry.put(
        // StrategyType.SUPERTREND,
        // new SuperTrendStrategyFactory());
    }

    public StrategyFactory getFactory(
            StrategyType type) {

        StrategyFactory factory =
                registry.get(type);

        if(factory == null){

            throw new IllegalArgumentException(
                    "No Strategy Registered : "
                            + type);
        }

        return factory;
    }

}