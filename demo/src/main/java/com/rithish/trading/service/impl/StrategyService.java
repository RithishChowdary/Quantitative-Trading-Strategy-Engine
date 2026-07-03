package com.rithish.trading.service.impl;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Strategy;

import com.rithish.trading.model.StrategyType;
import com.rithish.trading.strategy.StrategyRegistry;

public class StrategyService {

    private final StrategyRegistry registry =
            new StrategyRegistry();

    public Strategy getStrategy(
            StrategyType type,
            BarSeries series){

        return registry
                .getFactory(type)
                .create(series);
    }

}