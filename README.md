
# Quantitative Trading Strategy Engine 

A production-style quantitative trading engine built in Java using TA4J.

This project focuses on designing a reusable, modular, and extensible architecture for building, testing, and evaluating algorithmic trading strategies rather than creating a simple TA4J demo.

The long-term goal is to evolve this project into a complete quantitative trading platform capable of loading real market data, generating technical indicators, creating trading strategies, performing historical backtesting, analyzing performance metrics, and eventually supporting paper trading and live execution.

---

## Current Features

- Modular project architecture
- Strategy Factory pattern
- Indicator Factory pattern
- EMA Indicator
- RSI Indicator
- EMA + RSI Strategy
- Backtesting Engine
- Trade Reporting
- Dummy Market Data Loader
- Clean package structure with loose coupling

---

## Planned Features

### Phase 1
- Historical CSV Loader
- Twelve Data API Integration
- Yahoo Finance Loader
- Multiple Timeframe Support

### Phase 2
- Moving Average
- RSI
- MACD
- Volume
- VWAP
- ATR
- Supertrend
- CPR
- Bollinger Bands

### Phase 3
- Swing Trading Strategies
- Intraday Strategies
- Multi-indicator Strategies
- Risk Management

### Phase 4
- Portfolio Backtesting
- Performance Analytics
- Win Rate
- CAGR
- Maximum Drawdown
- Sharpe Ratio
- Sortino Ratio

### Phase 5
- Strategy Optimization
- Parameter Optimization
- Walk Forward Analysis
- Monte Carlo Simulation

### Phase 6
- Paper Trading
- Live Data Streaming
- Real-time Signal Generation
- Broker Integration

---

## Technologies

- Java 21
- Maven
- TA4J
- Object-Oriented Design
- Strategy Pattern
- Factory Pattern

---

## Project Structure
src
│
├── api          // Twelve Data / AlphaVantage / Polygon clients
├── backtest     // Performance analyzers, metrics
├── config
├── constant
├── contracts
├── dto
├── engine
├── exceptions
├── indicator
├── loader
├── model
├── report
├── service
├── strategy
├── util
└── App.java


---

## Vision

This project is being developed as a complete quantitative trading engine inspired by the architecture used in professional trading systems.

The emphasis is on clean architecture, maintainability, extensibility, and reusable components rather than writing one large monolithic application.

The final objective is to build an end-to-end platform capable of supporting research, backtesting, optimization, paper trading, and live algorithmic trading.
