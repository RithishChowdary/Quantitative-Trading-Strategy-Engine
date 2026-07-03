package com.rithish.trading.indicator;

import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.rithish.trading.contracts.IndicatorFactory;

public class RSIFactory implements IndicatorFactory{

    public RSIIndicator create(ClosePriceIndicator closePrice,
                               int period) {

        return new RSIIndicator(
                closePrice,
                period);
    }

}