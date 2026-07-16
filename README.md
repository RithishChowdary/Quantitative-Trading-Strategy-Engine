## 🎯 Vision
# Quantitative Trading Strategy Engine

> A production-style quantitative trading engine built in Java 21 using TA4J, designed as a reusable, modular, and extensible architecture for building, testing, and evaluating algorithmic trading strategies.

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![TA4J](https://img.shields.io/badge/TA4J-0.18-1F6FEB)
![Status](https://img.shields.io/badge/Status-Active%20Development-2EA043)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2F%20SOLID-8A2BE2)

## Overview

This project is a quantitative trading engine inspired by the architecture used in professional trading desks. It loads historical market data, generates technical indicators, composes strategies, runs deterministic backtests, and produces structured trade reports.

Unlike a typical TA4J demo, every component here is deliberately isolated behind contracts, registered through factories, and wired together through services. The result is a codebase that is easy to extend without rewriting existing modules, and a foundation that can grow from a console application into a full research, optimisation, paper-trading, and live-execution platform.

## Vision

The long-term objective is to evolve this project into a complete quantitative trading platform that supports research and indicator experimentation, deterministic historical backtesting, parameter optimisation and walk-forward analysis, portfolio-level analytics, paper trading, and live algorithmic execution.

The engine is designed to be reused as a library inside a larger AI-powered investment platform that may also include risk analysis, news sentiment, goal-based planning, and portfolio management.

## Design Principles

The architecture is grounded in software engineering fundamentals rather than trading specifics.

- **Clean Architecture** — strict layering, dependencies point inward toward contracts.
- **SOLID** — every layer has a single responsibility and is open for extension but closed for modification.
- **Strategy Pattern** — trading strategies are interchangeable behaviours.
- **Factory Pattern** — indicators and strategies are instantiated through registries.
- **Interface-driven Design** — every collaborator is bound by an interface, never by a concrete class.
- **Loose Coupling, High Cohesion** — modules know only what their contract exposes.
- **Reusability, Scalability, Maintainability** — the engine is intended to be lifted into larger systems as a self-contained module.

## High-Level Architecture

The engine is organised into cooperating layers. Each layer talks to the layer below it only through the contracts exposed by `com.rithish.trading.contracts`.

```mermaid
flowchart TB
    subgraph Presentation["Presentation Layer"]
        CLI["Console App<br/>(future: REST, WebSocket, Swagger)"]
    end
    subgraph Application["Application Layer"]
        APP["App.java<br/>Entry point, composition root"]
    end
    subgraph Service["Service Layer"]
        HDS["HistoricalDataService"]
        SS["StrategyService"]
    end
    subgraph Engine["Engine Layer"]
        BE["BacktestEngine"]
        TR["TradeReport"]
    end
    subgraph Strategy["Strategy Layer"]
        SR["StrategyRegistry"]
        SF["StrategyFactory"]
    end
    subgraph Indicator["Indicator Layer"]
        IR["IndicatorRegistry"]
        IF["IndicatorFactory"]
    end
    subgraph MarketData["Market Data Layer"]
        ML["MarketDataLoader"]
    end
    subgraph Historical["Historical Data Layer"]
        HDD["HistoricalDataDownloader"]
    end
    subgraph DTO["DTO Layer"]
        HC["HistoricalCandle"]
    end
    subgraph Config["Configuration Layer"]
        AC["ApiConfig"]
        SC["StrategyConfig"]
    end
    subgraph Util["Utility Layer"]
        CW["CsvWriter"]
    end
    subgraph Resources["Resources"]
        CSV["NSE/CSV Files"]
        PROPS["application.properties"]
    end

    CLI --> APP
    APP --> SS
    APP --> HDS
    APP --> BE
    APP --> TR
    SS --> SR
    SR --> SF
    HDS --> ML
    HDS --> HDD
    HDD --> CW
    CW --> CSV
    ML --> CSV
    ML --> HC
    BE --> SF
    SF --> IF
    IF --> IR
    APP --> AC
    SF --> SC
```

## Package Diagram

The package layout mirrors the layered architecture and keeps each concern isolated.

```mermaid
graph TB
    subgraph com_rithish_trading["com.rithish.trading"]
        P_APP["App.java"]
        P_CFG["config<br/>ApiConfig, StrategyConfig"]
        P_CON["contracts<br/>MarketDataLoader, StrategyFactory,<br/>IndicatorFactory, HistoricalDataDownloader"]
        P_DTO["dto.historical<br/>HistoricalCandle"]
        P_ENG["engine<br/>BacktestEngine"]
        P_IND["indicator<br/>EMAFactory, RSIFactory, IndicatorRegistry"]
        P_LD["loader<br/>CsvMarketDataLoader, IndianApiHistoricalLoader"]
        P_MD["model<br/>IndicatorType, StrategyType"]
        P_RPT["report<br/>TradeReport"]
        P_SVC["service<br/>HistoricalDataService, StrategyService,<br/>HistoricalDataDownloader"]
        P_STG["strategy<br/>EmaRsiStrategyFactory, StrategyRegistry"]
        P_UTL["util<br/>CsvWriter"]
    end
    subgraph External
        TA4J["TA4J Core"]
    end

    P_APP --> P_SVC
    P_APP --> P_ENG
    P_APP --> P_RPT
    P_APP --> P_LD
    P_SVC --> P_STG
    P_STG --> P_CON
    P_STG --> P_IND
    P_IND --> P_CON
    P_LD --> P_CON
    P_LD --> P_DTO
    P_ENG --> TA4J
    P_IND --> TA4J
    P_STG --> TA4J
    P_CFG -.binds.-> P_APP
```

## Data Flow

The end-to-end data flow is linear and deterministic. Each step transforms the data into a richer shape without leaking abstractions.

```mermaid
sequenceDiagram
    actor User
    participant App
    participant Loader as MarketDataLoader
    participant Downloader as HistoricalDataDownloader
    participant Service as StrategyService
    participant Registry as StrategyRegistry
    participant Factory as StrategyFactory
    participant Indicators as IndicatorRegistry
    participant Engine as BacktestEngine
    participant Reporter as TradeReport

    User->>App: launch(symbol, strategyType)
    opt first run or refresh
        App->>Downloader: download(symbol)
        Downloader->>Downloader: fetch JSON from Indian API
        Downloader->>Downloader: persist to CSV
    end
    App->>Loader: loadSeries(symbol)
    Loader->>Loader: read CSV
    Loader->>Loader: map rows to HistoricalCandle
    Loader->>Loader: build TA4J BarSeries
    Loader-->>App: BarSeries
    App->>Service: getStrategy(type, series)
    Service->>Registry: getFactory(type)
    Registry-->>Service: StrategyFactory
    Service->>Factory: create(series)
    Factory->>Indicators: getFactory(EMA), getFactory(RSI)
    Indicators-->>Factory: EMAFactory, RSIFactory
    Factory-->>Service: Strategy
    App->>Engine: run(series, strategy)
    Engine-->>App: TradingRecord
    App->>Reporter: print(series, record)
    Reporter-->>User: structured trade report
```

## Component View

```mermaid
graph LR
    User([Trader / Researcher])
    subgraph Core["Quant Engine"]
        A[App]
        SVC[Services]
        ENG[Backtest Engine]
        STR[Strategies]
        IND[Indicators]
        LD[Loaders]
        RPT[Report]
    end
    Data[(Historical Data<br/>CSV / API / DB)]
    User --> A
    A --> SVC
    SVC --> STR
    SVC --> LD
    A --> ENG
    ENG --> STR
    ENG --> RPT
    LD --> Data
    STR --> IND
```

## Module Breakdown

### Application Layer
`App` is the composition root. It selects the data source, picks the strategy from the registry, runs the backtest engine, and prints the report. It contains no business logic.

### Contracts Layer
The `contracts` package defines the boundaries of the system: `MarketDataLoader`, `StrategyFactory`, `IndicatorFactory`, and `HistoricalDataDownloader`. They enable dependency injection, make every collaborator swappable, and keep the engine framework-agnostic.

### Configuration Layer
`ApiConfig` and `StrategyConfig` isolate credentials, base URLs, indicator periods, and thresholds. Future iterations will read from YAML and environment variables. Isolation lets the same binary run in research, paper, and live modes without code changes.

### Historical Data Layer
`HistoricalDataDownloader` fetches OHLCV JSON from the Indian API and persists it to CSV through `CsvWriter`. Persisting to CSV is intentional — it makes backtests faster, deterministic, offline, repeatable, and independent of any third-party API.

### Market Data Layer
`CsvMarketDataLoader` reads CSV files, maps rows to `HistoricalCandle`, and assembles a TA4J `BarSeries`. Future loaders — Yahoo, Polygon, Twelve Data, Database, Cache — implement the same `MarketDataLoader` contract, so the engine always receives an identical `BarSeries`.

### DTO Layer
`HistoricalCandle` is a plain data carrier between the CSV and JSON boundary and TA4J. It keeps the loader free of TA4J types and lets the data shape evolve independently.

### Indicator Layer
`EMAFactory` and `RSIFactory` build indicators through the `IndicatorFactory` contract and are wired by `IndicatorRegistry`. The Factory Pattern is the single extension point: adding MACD, ATR, Bollinger Bands, VWAP, or Supertrend means adding a new factory and registering it — no existing code changes.

### Strategy Layer
`EmaRsiStrategyFactory` composes indicators into entry and exit rules and returns a TA4J `Strategy`. `StrategyRegistry` maps `StrategyType` enums to factories, so the engine never knows which strategy it is running. Adding a new strategy requires zero changes to `BacktestEngine`.

### Engine Layer
`BacktestEngine` accepts a `BarSeries` and a `Strategy` and returns a `TradingRecord`. It is intentionally ignorant of CSV, APIs, indicators, and UI — the engine can be reused unchanged inside a microservice, a CLI, or a notebook.

### Report Layer
`TradeReport` formats the `TradingRecord` for the console. Future iterations will export CSV, Excel, JSON, PDF, HTML, and chart visualisations.

### Service Layer
Services orchestrate business workflows: `HistoricalDataService` and `StrategyService` today; `RiskAnalysisService`, `OptimizationService`, and `PortfolioAnalysisService` later.

### Utility Layer
`CsvWriter` centralises serialisation. Future helpers will cover dates, files, numbers, and JSON.

## Current Features

- Modular project architecture with clear package boundaries
- `StrategyFactory` and `IndicatorFactory` contracts
- `StrategyRegistry` and `IndicatorRegistry` for runtime wiring
- EMA and RSI indicators with a registry-driven Factory
- EMA + RSI strategy with crossover and RSI threshold rules
- `BacktestEngine` powered by TA4J's `BarSeriesManager`
- `TradeReport` for completed trades and open positions
- CSV market data loader backed by the resources folder
- Stub `IndianApiHistoricalLoader` for future API integration
- Loose coupling — every collaborator is bound by an interface

## Planned Features

### Phase 1 — Data Foundation
Historical CSV loader (NSE, NASDAQ, BSE), Twelve Data API integration, Yahoo Finance loader, multiple timeframe support (1m, 5m, 15m, 1h, 1d).

### Phase 2 — Indicator Library
Moving Average, RSI, MACD, Volume, VWAP, ATR, Supertrend, CPR, Bollinger Bands.

### Phase 3 — Strategy Library
Swing trading, intraday, multi-indicator, and risk-managed strategies.

### Phase 4 — Portfolio Analytics
Win rate, CAGR, maximum drawdown, Sharpe ratio, Sortino ratio, expectancy.

### Phase 5 — Optimisation
Parameter sweeps, walk-forward analysis, Monte Carlo simulation.

### Phase 6 — Live Trading
Paper trading, real-time signal generation, broker integration.

## Project Structure

```
demo
├── pom.xml
├── .gitignore
└── src
    └── main
        ├── java
        │   └── com
        │       └── rithish
        │           ├── practice         (TA4J learning scratchpad)
        │           └── trading
        │               ├── App.java
        │               ├── config         (ApiConfig, StrategyConfig)
        │               ├── contracts      (MarketDataLoader, StrategyFactory, IndicatorFactory, HistoricalDataDownloader)
        │               ├── dto.historical (HistoricalCandle)
        │               ├── engine         (BacktestEngine)
        │               ├── indicator      (EMAFactory, RSIFactory, IndicatorRegistry)
        │               ├── loader         (CsvMarketDataLoader, IndianApiHistoricalLoader)
        │               ├── model          (IndicatorType, StrategyType)
        │               ├── report         (TradeReport)
        │               ├── service
        │               │   ├── downloader (HistoricalDataDownloader)
        │               │   └── impl       (HistoricalDataService, StrategyService)
        │               ├── strategy       (EmaRsiStrategyFactory, StrategyRegistry)
        │               └── util           (CsvWriter)
        └── resources
            └── historical
                └── NSE
                    ├── INFY.csv
                    ├── RELIANCE.csv
                    └── TCS.csv
```

## Technology Stack

- Java 21
- Maven
- TA4J 0.18 — core technical analysis engine
- Project Lombok
- Jackson — JSON parsing
- OkHttp 4.12 — HTTP client
- Logback — logging

## Getting Started

Prerequisites: JDK 21 and Maven 3.9+.

```bash
git clone https://github.com/<your-username>/quantitative-trading-strategy-engine.git
cd quantitative-trading-strategy-engine/demo
mvn clean compile
mvn exec:java -Dexec.mainClass="com.rithish.trading.App"
```

Place OHLCV CSV files under `src/main/resources/historical/NSE/` using the format `Date,Open,High,Low,Close,Volume`.

## SOLID Principles by Layer

| Layer | S — Single Responsibility | O — Open/Closed | L — Liskov Substitution | I — Interface Segregation | D — Dependency Inversion |
| --- | --- | --- | --- | --- | --- |
| Application | One entry point that composes dependencies | Strategies and loaders are pluggable | Substitutable via service abstractions | Narrow app-level interfaces | Depends on services, never concretes |
| Contracts | Pure interfaces, no behaviour | New implementations extend the system | Any implementation satisfies the contract | Cohesive per-purpose interfaces | Inversion: app depends on contracts |
| Configuration | Single source for each concern | Add new config files without code change | n/a | Cohesive config domains | Injected into the layers that need them |
| Loader | One transport per implementation | New loaders added without touching the engine | Any `MarketDataLoader` works | Just `loadSeries` per contract | Injected into the service |
| Indicator | One indicator per factory | Add a factory and register it | Any `IndicatorFactory` works | Minimal factory surface | Engine depends on `Indicator<Num>` |
| Strategy | One strategy per factory | Register a new `StrategyType` to add behaviour | Any factory output is a valid `Strategy` | Only `create(BarSeries)` | Engine depends on the contract |
| Engine | Only orchestrates a backtest | New engines (walk-forward, Monte Carlo) extend it | Any `Strategy` is acceptable | Single method: `run` | Inversion: receives inputs by contract |
| Report | One output format per class | Add new report formats freely | All formats accept the same record | Per-format interfaces | Engine knows nothing about reports |
| Service | One workflow per service | New services extend workflows | Substitutable collaborators | Minimal orchestration surface | Wires the contracts together |
| Utility | Stateless helpers | New helpers added freely | n/a | n/a | n/a |

## Extension Points

Adding new behaviour never requires modifying existing code.

- **New indicator** — implement `IndicatorFactory`, register in `IndicatorRegistry`, add a value to `IndicatorType`.
- **New strategy** — implement `StrategyFactory`, register in `StrategyRegistry`, add a value to `StrategyType`.
- **New data source** — implement `MarketDataLoader`, inject into `HistoricalDataService`.
- **New API** — implement `HistoricalDataDownloader`, add credentials to `ApiConfig`.
- **New risk rule** — add a `RiskAnalyzer` contract and inject it into the engine.
- **New report format** — add a `ReportGenerator` implementation.
- **New optimiser** — add an `Optimizer` contract and call the engine in a loop.
- **AI, portfolio, live trading** — expose the engine as a library and call its services.

## Testing Architecture

Every layer is independently testable because collaborators are bound by interfaces.

- **Unit tests** — `EMAFactory`, `RSIFactory`, `EmaRsiStrategyFactory`, `CsvMarketDataLoader`, `CsvWriter`.
- **Indicator tests** — known-input, known-output fixtures against TA4J references.
- **Strategy tests** — synthetic `BarSeries` with deterministic signals.
- **Backtest tests** — golden-position fixtures for the engine.
- **Loader tests** — temp CSV files and content-based assertions.
- **API tests** — WireMock or recorded responses around `HistoricalDataDownloader`.
- **Integration tests** — full pipeline from CSV to `TradeReport` with fixed fixtures.

## Future Platform Vision

The engine is designed to be lifted into a larger platform without physical merger — every consumer depends on the same contracts.

```mermaid
graph TB
    subgraph Platform["Larger Investment Platform"]
        PM[Portfolio Management]
        AI[AI Assistant]
        RE[Risk Engine]
        NA[News Analyzer]
        GP[Goal Planner]
        PT[Paper Trading]
        LT[Live Trading]
    end
    QE["Quant Engine Library<br/>(this project)"]
    PM --> QE
    AI --> QE
    RE --> QE
    NA --> QE
    GP --> QE
    PT --> QE
    LT --> QE
```

## Deployment Roadmap

The engine runs as a console application today. Future deployments layer in infrastructure without changing the existing modules.

- **Containerisation** — Docker images for the engine, the API, and workers.
- **Cloud** — AWS (ECS or Fargate, S3 for data, Secrets Manager for keys).
- **CI/CD** — GitHub Actions for build, test, package, and deploy.
- **Observability** — Spring Boot Actuator, Micrometer, Prometheus, Grafana.
- **Caching and Streaming** — Redis for hot series, Kafka for tick streams and signals.
- **Web surface** — Spring Boot REST plus WebSocket plus Swagger for strategy control.

Because every collaborator is bound by an interface, the engine can move from a CLI to Spring Boot to a microservice without touching `BacktestEngine`, `StrategyFactory`, or any indicator code.

## Contributing

Issues and pull requests that preserve the layering, respect the contracts, and add test coverage are welcome. New features should arrive as new modules and registries, not edits to existing ones.

## Why this project exists?
Most TA4J examples demonstrate indicators in isolation. This project focuses on building a reusable quantitative trading engine with production-style architecture, allowing strategies, indicators, and data providers to be extended independently.
