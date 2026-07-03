package com.rithish.trading.strategy;

import com.rithish.trading.config.StrategyConfig;
import com.rithish.trading.contracts.StrategyFactory;
import com.rithish.trading.indicator.EMAFactory;
import com.rithish.trading.indicator.RSIFactory;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;

import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.averages.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.rules.OverIndicatorRule;
import org.ta4j.core.rules.UnderIndicatorRule;

public class EmaRsiStrategyFactory implements StrategyFactory {

    @Override
    public Strategy create(BarSeries series) {

        // Configuration
        StrategyConfig config = new StrategyConfig();

        // Close Price Indicator
        ClosePriceIndicator closePrice =
                new ClosePriceIndicator(series);

        // Indicator Factories
        EMAFactory emaFactory = new EMAFactory();
        RSIFactory rsiFactory = new RSIFactory();

        // EMA Indicators
        EMAIndicator emaFast =
                emaFactory.create(
                        closePrice,
                        config.getFastEma());

        EMAIndicator emaSlow =
                emaFactory.create(
                        closePrice,
                        config.getSlowEma());

        // RSI Indicator
        RSIIndicator rsi =
                rsiFactory.create(
                        closePrice,
                        config.getRsiPeriod());

        // Buy Rule
        Rule buyRule =
                new CrossedUpIndicatorRule(
                        emaFast,
                        emaSlow)
                        .and(
                                new OverIndicatorRule(
                                        rsi,
                                        config.getRsiBuy()));

        // Sell Rule
        Rule sellRule =
                new CrossedDownIndicatorRule(
                        emaFast,
                        emaSlow)
                        .or(
                                new UnderIndicatorRule(
                                        rsi,
                                        config.getRsiSell()));

        // Return Strategy
        return new BaseStrategy(
                buyRule,
                sellRule);
    }
}