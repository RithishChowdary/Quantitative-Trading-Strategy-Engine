package com.rithish.trading.config;

public class StrategyConfig {

    private final int fastEma = 9;
    private final int slowEma = 26;
    private final int rsiPeriod = 14;
    private final int rsiBuy = 45;
    private final int rsiSell = 55;

    public int getFastEma() {
        return fastEma;
    }

    public int getSlowEma() {
        return slowEma;
    }

    public int getRsiPeriod() {
        return rsiPeriod;
    }

    public int getRsiBuy() {
        return rsiBuy;
    }

    public int getRsiSell() {
        return rsiSell;
    }
}