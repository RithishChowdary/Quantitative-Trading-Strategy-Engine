package com.rithish.trading.indicator;

import org.ta4j.core.indicators.averages.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.rithish.trading.contracts.IndicatorFactory;

public class EMAFactory implements IndicatorFactory{

    public EMAIndicator create(ClosePriceIndicator closePrice,
                               int period) {

        return new EMAIndicator(
                closePrice,
                period);
    }

}