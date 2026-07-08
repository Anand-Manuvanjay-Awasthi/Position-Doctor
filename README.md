# Position Doctor

Position Doctor is a full-stack hackathon project for tracking stock positions, evaluating their health, and turning that evaluation into clear portfolio signals.

This backend is intentionally built as a set of small domain modules. Each module answers one question:

- What positions does the user hold?
- How healthy is each position?
- What changed over time?
- What is the current market backdrop?
- Is the company fundamentally strong?
- What action should the user consider?
- What alerts should the dashboard show?
- What does the portfolio look like right now?

The project is still hackathon-sized, but the backend is organized as if a small engineering team will keep extending it after the demo.

---

## Contents

- [Project Status](#project-status)
- [Engineering Journal](#engineering-journal)
- [Architecture Diagram](#architecture-diagram)
- [Backend Overview](#backend-overview)
- [Technology Stack](#technology-stack)
- [Package Map](#package-map)
- [Domain Modules](#domain-modules)
- [Core Flow](#core-flow)
- [REST API Reference](#rest-api-reference)
- [Database Model](#database-model)
- [Realtime Refresh](#realtime-refresh)
- [Local Setup](#local-setup)
- [PostgreSQL Setup](#postgresql-setup)
- [Running The Application](#running-the-application)
- [Useful Test Sequence](#useful-test-sequence)
- [Design Notes](#design-notes)
- [JAVA_HOME Troubleshooting](#java_home-troubleshooting)
- [Future Work](#future-work)

---

## Project Status

Implemented backend modules:

| Area | Status | Notes |
|---|---:|---|
| Position Management | Done | CRUD-style position APIs |
| Position Health Engine | Done | Deterministic health score from position risk, performance, and stability |
| Health History | Done | Persists health snapshots over time |
| Market Context Layer | Done | Fear & Greed Index only |
| Fundamental Strength Layer | Done | EPS and ROE only |
| Recommendation Engine | Done | Rule engine, not a scoring engine |
| Plain Language Rationales | Done | Deterministic explanations |
| Realtime Update Engine | Done | Scheduled refresh every 2 minutes |
| Alert Engine | Done | Persists recommendation-change alerts |
| Daily Portfolio Digest | Done | Dashboard summary using latest persisted data |

Future enhancements :

- Authentication
- External market data providers
- Email, SMS, push notifications
- WebSockets
- Kafka, Redis, async messaging
- Historical portfolio analytics
- Recommendation personalization

---

## Engineering Journal

This project has an engineering journal that explains the decisions, tradeoffs, and reasoning behind the backend design.

```text
ENGINEERING_JOURNAL.md
```

[Read the Engineering Journal](ENGINEERING_JOURNAL.md)

---

## Architecture Diagram

<img width="1312" height="1229" alt="image" src="https://github.com/user-attachments/assets/0d2edc58-828c-41a4-a0f6-3d1ea8720e66" />


Diagram placeholder:

```text
+-------------------+      +-------------------+
| Position APIs     |      | Market Context    |
+---------+---------+      +---------+---------+
          |                          |
          v                          v
+-------------------+      +-------------------+
| Health Engine     |      | Fundamentals      |
+---------+---------+      +---------+---------+
          |                          |
          v                          |
+-------------------+                |
| Health History    |                |
+---------+---------+                |
          |                          |
          +------------+-------------+
                       v
             +-------------------+
             | Recommendation    |
             | Rule Engine       |
             +---------+---------+
                       |
                       v
             +-------------------+
             | Rationales        |
             | Alerts            |
             | Digest            |
             +-------------------+
```

---

## Backend Overview

Position Doctor evaluates a stock position from several independent angles:

1. **Position Health**
   Measures how the current position is behaving using risk, performance, and stability.

2. **Market Context**
   Stores and reports Fear & Greed Index readings.

3. **Fundamental Strength**
   Evaluates company quality using EPS and ROE only.

4. **Recommendation Engine**
   Applies explicit rules to health and market context to produce one primary action and optional secondary actions.

5. **Plain Language Rationales**
   Explains the already-decided recommendations in dashboard-friendly language.

6. **Realtime Update Engine**
   Refreshes active positions every 2 minutes and persists new health snapshots.

7. **Alert Engine**
   Creates alerts when the primary recommendation changes.

8. **Daily Digest**
   Aggregates the latest persisted state into a portfolio dashboard summary.

The important design boundary is this:

```text
Health decides health.
Market Context describes the market backdrop.
Fundamentals describe company quality.
Recommendations decide action.
Rationales explain decisions.
Digest summarizes persisted state.
```

---

## Technology Stack

### Backend

| Tool | Version / Detail | Purpose |
|---|---|---|
| Java | 17 | Backend language and runtime |
| Spring Boot | 4.1.0 | Application framework |
| Spring Web MVC | `spring-boot-starter-webmvc` | REST API layer |
| Spring Data JPA | `spring-boot-starter-data-jpa` | Repository and persistence abstraction |
| Hibernate | Managed by Spring Boot | ORM for PostgreSQL tables |
| PostgreSQL Driver | `org.postgresql:postgresql` | JDBC driver |
| Jakarta Validation | `spring-boot-starter-validation` | Bean validation for request DTOs and entities |
| Lombok | `org.projectlombok:lombok` | Reduces DTO/entity/service boilerplate |
| Maven Wrapper | `mvnw`, `mvnw.cmd` | Reproducible backend build without installing Maven globally |

Backend Maven dependencies are intentionally small:

- `spring-boot-starter-webmvc`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `postgresql`
- `lombok`

### Frontend

| Tool | Version / Detail | Purpose |
|---|---|---|
| React | 19.x | UI framework |
| React DOM | 19.x | Browser rendering |
| React Router DOM | 7.x | Client-side routing |
| TypeScript | 5.7.x | Static typing |
| Vite | 6.x | Dev server and production build |
| Axios | 1.7.x | HTTP client for backend APIs |
| Tailwind CSS | 3.4.x | Utility-first styling |
| PostCSS / Autoprefixer | 8.x / 10.x | CSS processing |
| npm | Comes with Node.js | Frontend dependency and script runner |

Frontend scripts:

```bash
npm run dev      # start Vite dev server
npm run build    # TypeScript check + production build
npm run preview  # preview production build
```

---

## Package Map

Main package:

```text
org.example.positiondoctor
```

Current package layout:

```text
positiondoctor
├── Controller
├── DTO
├── Repository
├── Service
├── alert
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── exception
│   ├── mapper
│   ├── repository
│   └── service
├── digest
│   ├── controller
│   ├── dto
│   └── service
├── entities
├── exception
├── fundamentalStrengthLayer
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── enums
│   ├── evaluator
│   ├── exception
│   ├── mapper
│   ├── repository
│   └── service
├── health
│   ├── constraint
│   ├── controller
│   ├── dto
│   ├── enums
│   ├── evaluator
│   ├── history
│   ├── mapper
│   └── service
├── marketcontext
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── enums
│   ├── evaluator
│   ├── exception
│   ├── mapper
│   ├── repository
│   └── service
├── realtime
│   ├── scheduler
│   └── service
└── recommendation
    ├── confidence
    ├── controller
    ├── dto
    ├── enums
    ├── explanation
    ├── mapper
    ├── rationale
    ├── rule
    └── service
```

Some early packages use uppercase names such as `Controller`, `DTO`, `Repository`, and `Service`. Newer modules use lowercase package names. The code still compiles as long as imports match the existing package structure.

---

## Domain Modules

### 1. Position Management

Position Management stores the user's stock positions.

Entity:

```text
Position
```

Fields:

- `id`
- `stockSymbol`
- `quantity`
- `buyPrice`
- `currentPrice`
- `targetPrice`
- `stopLoss`
- `createdAt`
- `active`

The `active` flag is used by the realtime refresh and digest modules.

Endpoints:

```text
POST   /positions
GET    /positions
GET    /positions/{id}
DELETE /positions/{id}
```

---

### 2. Position Health Engine

The Health Engine evaluates the condition of a position. It does not evaluate market sentiment or fundamentals.

Score model:

| Category | Range | Inputs |
|---|---:|---|
| Risk Score | 0-40 | Trend, distance to stop loss |
| Performance Score | 0-40 | P&L, distance to target |
| Stability Score | 0-20 | Volatility |

Total:

```text
healthScore = riskScore + performanceScore + stabilityScore
```

Main output:

```text
PositionHealthReport
```

Includes:

- `healthScore`
- `healthStatus`
- `riskLevel`
- `fluctuationLevel`
- `riskScore`
- `performanceScore`
- `stabilityScore`
- `factorBreakdown`

Important classes:

- `HealthScoreService`
- `RiskEvaluator`
- `PerformanceEvaluator`
- `StabilityEvaluator`
- `HealthConstraintProcessor`

Hard constraints are intentionally isolated in `HealthConstraintProcessor`.
More about the Health engine in this link : https://docs.google.com/document/d/17hykGhIfxSPzbseLS-hrfQCr0vJp8vPPE0efQv1EOzg/edit?usp=sharing
---

### 3. Health History

Health History stores point-in-time snapshots of health evaluations.

Entity:

```text
PositionHealthSnapshot
```

Stored values include:

- Position id
- Health score
- Risk score
- Performance score
- Stability score
- Health status
- Risk level
- Fluctuation level
- Primary recommendation
- Recommendation confidence
- Created timestamp

This module is used by:

- Latest health APIs
- Realtime refresh
- Alert change detection
- Daily digest

---

### 4. Market Context Layer

Market Context stores the market backdrop. The current implementation supports Fear & Greed Index only.

Entity:

```text
MarketContextSnapshot
```

Input:

```text
fearGreedIndex: 0-100
```

Output:

```text
MarketContextReport
```

Fear & Greed levels:

| Score | Level |
|---:|---|
| 0-24 | EXTREME_FEAR |
| 25-44 | FEAR |
| 45-55 | NEUTRAL |
| 56-75 | GREED |
| 76-100 | EXTREME_GREED |

Market Context does not generate recommendations. It provides facts for the Recommendation Engine.

---

### 5. Fundamental Strength Layer

Fundamental Strength evaluates the company behind the stock.

Inputs:

- EPS
- ROE

Output:

```text
FundamentalStrengthReport
```

Fields:

- `strengthScore`
- `strengthLevel`
- `eps`
- `roe`
- `explanation`

Strength levels:

- `WEAK`
- `MODERATE`
- `STRONG`

This module does not override recommendations. The Recommendation Engine includes fundamental strength as an independent company quality indicator.

---

### 6. Recommendation Engine

The Recommendation Engine is a rule engine.

It is not another scoring engine.

It consumes:

- `Position`
- `PositionHealthReport`
- `MarketContextReport`
- `FundamentalStrengthReport`

It returns:

```text
RecommendationResponse
```

Primary recommendations:

- `STRONG_HOLD`
- `HOLD`
- `WATCH_CLOSELY`
- `REDUCE_POSITION`
- `CONSIDER_EXIT`

Secondary recommendations:

- `BOOK_PROFIT`
- `TIGHTEN_STOP_LOSS`
- `HEDGE_POSITION`

Confidence is derived from:

```text
healthScore + market modifier
```

Fundamentals are not used to select the recommendation. They are included in the response separately.

---

### 7. Plain Language Rationales

Rationales are generated by deterministic `switch` statements.

Main class:

```text
RecommendationExplanationGenerator
```

Output:

```text
RecommendationExplanation
```

Fields:

- `recommendationRationale`
- `companyInsights`

No external services are used. No prompt-based text generation is used.

---

### 8. Realtime Update Engine

Realtime updates are simulated with Spring scheduling.

Scheduling is enabled in:

```text
PositionDoctorApplication
```

Scheduler:

```text
RealtimeScheduler
```

Runs every 2 minutes:

```java
@Scheduled(fixedRate = 120000)
```

The scheduler only calls:

```java
realtimeUpdateService.refreshAllPositions();
```

The business flow lives in `RealtimeUpdateServiceImpl`.

Refresh flow:

1. Fetch all active positions.
2. Recalculate health.
3. Generate recommendation.
4. Persist a new health snapshot.
5. Compare previous primary recommendation with current primary recommendation.
6. Create an alert if the primary recommendation changed.
7. Continue processing remaining positions even if one position fails.

---

### 9. Alert Engine

The Alert Engine persists recommendation-change alerts.

Alert creation rule:

```text
Create an alert only when the primary recommendation changes.
```

Example:

```text
HOLD -> WATCH_CLOSELY = alert
WATCH_CLOSELY -> WATCH_CLOSELY = no alert
```

Entity:

```text
Alert
```

Fields:

- `id`
- `positionId`
- `stockSymbol`
- `previousRecommendation`
- `currentRecommendation`
- `message`
- `createdAt`
- `isRead`

There is no notification delivery. The frontend fetches alerts from the API.

---

### 10. Daily Portfolio Digest

The digest is a read-only dashboard summary.

It does not recalculate health.
It does not regenerate recommendations.

It uses:

- Active positions
- Latest persisted health snapshots
- Unread alerts

Output:

```text
PortfolioDigestResponse
```

Includes:

- Total positions
- Healthy positions
- Watch closely count
- Reduce position count
- Consider exit count
- Average health score
- Highest health position
- Lowest health position
- Highest confidence recommendation
- Lowest confidence recommendation
- Unread alerts count

---

## Core Flow

### Manual evaluation flow

```text
Client
  -> Position API
  -> Health API
  -> Recommendation API
  -> Response with recommendation and explanation
```

### Scheduled refresh flow

```text
RealtimeScheduler
  -> RealtimeUpdateService
  -> PositionRepository
  -> HealthScoreService
  -> MarketContextService
  -> FundamentalStrengthService
  -> RecommendationEngineService
  -> HealthHistoryService
  -> AlertService
```

### Dashboard flow

```text
Frontend Dashboard
  -> GET /api/v1/digest
  -> PortfolioDigestService
  -> PositionRepository
  -> HealthHistoryService
  -> AlertService
```

---

## REST API Reference

Base URL for local development:

```text
http://localhost:8080
```

### Position APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/positions` | Create a position from the full frontend form |
| GET | `/api/v1/positions` | Get stored position records |
| GET | `/api/v1/positions/summaries` | Get portfolio table summaries |
| GET | `/api/v1/positions/{id}` | Get one stored position record |
| GET | `/api/v1/positions/{id}/details` | Get frontend-ready position details |
| DELETE | `/api/v1/positions/{id}` | Delete one position |

Example request:

```json
{
  "stockSymbol": "AAPL",
  "quantity": 10,
  "buyPrice": 100.00,
  "currentPrice": 120.00,
  "targetPrice": 130.00,
  "stopLoss": 95.00,
  "trend": "UPTREND",
  "fearGreedIndex": 42,
  "eps": 6.12,
  "roe": 28.40
}
```

---

### Health APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/health/{positionId}` | Evaluate stored position health and save snapshot |
| POST | `/api/v1/health/evaluate` | Evaluate a temporary position without saving it |
| GET | `/api/v1/health/{positionId}/history` | Get health history |
| GET | `/api/v1/health/{positionId}/latest` | Get latest health snapshot |

Example sandbox request:

```json
{
  "stockSymbol": "AAPL",
  "quantity": 10,
  "buyPrice": 100.00,
  "currentPrice": 120.00,
  "targetPrice": 130.00,
  "stopLoss": 95.00
}
```

Example health response:

```json
{
  "healthScore": 77,
  "healthStatus": "HEALTHY",
  "riskLevel": "SAFE",
  "fluctuationLevel": "MODERATE",
  "riskScore": 30,
  "performanceScore": 35,
  "stabilityScore": 12,
  "factorBreakdown": {
    "trendContribution": 12,
    "stopLossContribution": 18,
    "pnlContribution": 20,
    "targetContribution": 15,
    "volatilityContribution": 12
  }
}
```

---

### Market Context APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/market-context/fear-greed` | Store Fear & Greed Index |
| GET | `/api/v1/market-context/latest` | Get latest market context |
| GET | `/api/v1/market-context/history` | Get market context history |

Example request:

```json
{
  "fearGreedIndex": 72
}
```

Example response:

```json
{
  "id": 1,
  "fearGreedIndex": 72,
  "fearGreedLevel": "GREED",
  "createdAt": "2026-07-01T10:30:00"
}
```

---

### Fundamental APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/fundamentals` | Store EPS and ROE for a stock |
| GET | `/api/v1/fundamentals/{stockSymbol}` | Evaluate latest fundamentals |

Example request:

```json
{
  "stockSymbol": "AAPL",
  "eps": 6.12,
  "roe": 28.40
}
```

Example response:

```json
{
  "strengthScore": 100,
  "strengthLevel": "STRONG",
  "eps": 6.12,
  "roe": 28.40,
  "explanation": "The company demonstrates strong profitability and efficient capital utilization."
}
```

---

### Recommendation APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/recommendations/{positionId}` | Generate latest recommendation for one position |

Example response:

```json
{
  "primaryRecommendation": "HOLD",
  "secondaryRecommendations": [
    "TIGHTEN_STOP_LOSS"
  ],
  "confidence": 80,
  "rationale": "The position can be held, but conditions should continue to be monitored. The position is profitable, so tightening the stop-loss can help protect gains.",
  "explanation": {
    "recommendationRationale": "The position can be held, but conditions should continue to be monitored. The position is profitable, so tightening the stop-loss can help protect gains.",
    "companyInsights": "The company demonstrates strong profitability and efficient capital utilization."
  },
  "fundamentalStrength": "STRONG",
  "healthScore": 90,
  "fearGreedLevel": "FEAR"
}
```

---

### Alert APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/alerts` | Get all alerts |
| GET | `/api/v1/alerts/unread` | Get unread alerts |
| PATCH | `/api/v1/alerts/{id}/read` | Mark alert as read |

Example response:

```json
[
  {
    "stockSymbol": "AAPL",
    "previousRecommendation": "HOLD",
    "currentRecommendation": "WATCH_CLOSELY",
    "message": "Holding recommendation has changed to WATCH_CLOSELY due to declining position health.",
    "createdAt": "2026-07-01T11:00:00",
    "isRead": false
  }
]
```

---

### Digest API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/v1/digest` | Get current portfolio dashboard summary |

Example response:

```json
{
  "totalPositions": 5,
  "healthyPositions": 2,
  "watchCloselyPositions": 1,
  "reducePositionRecommendations": 1,
  "considerExitRecommendations": 1,
  "averageHealthScore": 67.4,
  "highestHealthPosition": "MSFT",
  "highestHealthScore": 91,
  "lowestHealthPosition": "TSLA",
  "lowestHealthScore": 35,
  "highestConfidencePosition": "MSFT",
  "highestConfidenceRecommendation": "STRONG_HOLD",
  "highestConfidence": 96,
  "lowestConfidencePosition": "TSLA",
  "lowestConfidenceRecommendation": "CONSIDER_EXIT",
  "lowestConfidence": 30,
  "unreadAlerts": 3
}
```

---

## Database Model

Hibernate is currently configured with:

```properties
spring.jpa.hibernate.ddl-auto=update
```

That means tables are created and updated automatically during development.

Main tables:

| Table | Purpose |
|---|---|
| `positions` | User stock positions |
| `position_health_snapshots` | Health and recommendation snapshots |
| `market_context_snapshots` | Fear & Greed Index snapshots |
| `fundamental_metrics` | EPS and ROE records |
| `alerts` | Recommendation-change alerts |

Useful indexes already defined in entities:

| Table | Index |
|---|---|
| `position_health_snapshots` | `position_id` |
| `position_health_snapshots` | `position_id, created_at` |
| `market_context_snapshots` | `created_at` |
| `fundamental_metrics` | `stock_symbol` |
| `fundamental_metrics` | `stock_symbol, created_at` |
| `alerts` | `position_id` |
| `alerts` | `created_at` |

---

## Realtime Refresh

Scheduling is enabled with:

```java
@EnableScheduling
```

The refresh job runs every 2 minutes:

```java
@Scheduled(fixedRate = 120000)
```

The scheduler logs start and finish events. Per-position failures are caught inside `RealtimeUpdateServiceImpl`, so one bad position does not stop the rest of the portfolio refresh.

Important behavior:

- Refresh uses active positions only.
- Each refresh writes a new health snapshot.
- Alerts are created only when the primary recommendation changes.
- Notification delivery is intentionally not implemented.

---

## Local Setup

This repository contains both the Spring Boot backend and the React frontend.

```text
Position-Doctor/
├── src/          # Spring Boot backend
├── frontend/     # React + Vite frontend
├── pom.xml       # Backend Maven project
├── mvnw.cmd      # Maven wrapper for Windows
└── mvnw          # Maven wrapper for macOS/Linux
```

### Prerequisites

Install these before running the project:

| Requirement | Recommended Version | Used For |
|---|---:|---|
| JDK | 17 | Running and building the Spring Boot backend |
| Node.js | 20 LTS or newer | Running the React frontend |
| npm | Bundled with Node.js | Installing frontend packages |
| PostgreSQL | 14 or newer | Application database |
| pgAdmin | Optional | GUI for managing PostgreSQL |
| Git | Latest stable | Cloning and working with the repository |

Maven does not need to be installed globally. Use the Maven wrapper already included in the project.

### Verify local tools

From a terminal:

```powershell
java -version
javac -version
node -v
npm -v
```

Java must report version `17`. If `java` or `javac` is not found, fix `JAVA_HOME` before running the backend.

### Clone and open the project

```powershell
git clone <repository-url>
cd Position-Doctor
```

Open `D:\Projects\Position-Doctor` or your cloned folder in IntelliJ IDEA, VS Code, or your preferred editor.

---

## PostgreSQL Setup

The backend expects a PostgreSQL database named `position_doctor` by default.

### Option 1: pgAdmin

1. Start PostgreSQL.
2. Open pgAdmin.
3. Connect to your local PostgreSQL server.
4. Right-click `Databases`.
5. Select `Create` -> `Database`.
6. Use this database name:

```text
position_doctor
```

7. Save the database.

### Option 2: psql

```sql
CREATE DATABASE position_doctor;
```

### Backend database configuration

The backend reads database settings from environment variables:

| Variable | Required | Default |
|---|---|---|
| `DB_URL` | No | `jdbc:postgresql://localhost:5432/position_doctor` |
| `DB_USERNAME` | No | `postgres` |
| `DB_PASSWORD` | Yes, unless your local postgres user has no password | empty |

Set them in PowerShell before running the backend:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/position_doctor"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_postgres_password"
```

The matching Spring Boot configuration is:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/position_doctor}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=org.postgresql.Driver
```

For local development, Hibernate is configured with:

```properties
spring.jpa.hibernate.ddl-auto=update
```

That means the backend creates or updates tables automatically when the app starts.

---

## Running The Application

Run the backend and frontend in two separate terminals.

### 1. Start the backend

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

You can also run this class from IntelliJ:

```text
PositionDoctorApplication
```

The backend starts on:

```text
http://localhost:8080
```

Backend build/test command:

```powershell
.\mvnw.cmd test
```

### 2. Configure the frontend API URL

Create or update this file:

```text
frontend/.env
```

Use:

```env
VITE_API_BASE_URL=http://localhost:8080
```

### 3. Start the frontend

Open a second terminal:

```powershell
cd frontend
npm install
npm run dev
```

The frontend starts on:

```text
http://localhost:5173
```

Frontend production build command:

```powershell
npm run build
```

If PowerShell blocks `npm` with an execution policy error, use:

```powershell
npm.cmd run build
npm.cmd run dev
```

### 4. Local app URLs

| App | URL |
|---|---|
| Backend API | `http://localhost:8080` |
| Frontend | `http://localhost:5173` |
| Add Position page | `http://localhost:5173/add-position` |
| Portfolio page | `http://localhost:5173/portfolio` |
| Alerts page | `http://localhost:5173/alerts` |
| Daily Digest page | `http://localhost:5173/digest` |

---

## Useful Test Sequence

This sequence matches the current frontend flow. The Add Position form sends one request, and the backend facade orchestrates position creation, market context, fundamentals, health scoring, recommendation generation, and health snapshot persistence.

### 1. Create a position from the frontend

Open:

```text
http://localhost:5173/add-position
```

Submit the form with values such as:

```json
{
  "stockSymbol": "AAPL",
  "quantity": 10,
  "buyPrice": 100.00,
  "currentPrice": 120.00,
  "targetPrice": 130.00,
  "stopLoss": 95.00,
  "trend": "UPTREND",
  "fearGreedIndex": 42,
  "eps": 6.12,
  "roe": 28.40
}
```

Equivalent API call:

```http
POST /api/v1/positions
```

Supported `trend` values:

```text
STRONG_UPTREND
UPTREND
SIDEWAYS
DOWNTREND
STRONG_DOWNTREND
```

### 2. Open the portfolio

```text
http://localhost:5173/portfolio
```

Backend endpoint used by the frontend:

```http
GET /api/v1/positions/summaries
```

### 3. Open position details

Click `View Details` in the portfolio table.

Backend endpoint used by the frontend:

```http
GET /api/v1/positions/{id}/details
```

### 4. Open the daily digest

```text
http://localhost:5173/digest
```

Backend endpoint:

```http
GET /api/v1/digest
```

### 5. Open alerts

```text
http://localhost:5173/alerts
```

Backend endpoints:

```http
GET /api/v1/alerts
GET /api/v1/alerts/unread
PATCH /api/v1/alerts/1/read
```

### 6. Optional direct backend checks

These endpoints are still useful for manual backend testing:

```http
GET  /api/v1/health/{positionId}
POST /api/v1/health/evaluate
POST /api/v1/market-context/fear-greed
GET  /api/v1/market-context/latest
POST /api/v1/fundamentals
GET  /api/v1/fundamentals/{stockSymbol}
GET  /api/v1/recommendations/{positionId}
```

---

## Design Notes

### Why modules are separate

The project avoids placing all logic inside one service. Position health, market context, fundamentals, recommendations, alerts, and digest each have a separate reason to change.

### Why the Recommendation Engine is rule-based

The recommendation layer is meant to be explainable. It does not average scores or create hidden weights. It reads facts and applies rules.

### Why the digest uses persisted snapshots

The digest is a dashboard summary of the current persisted state. It does not recalculate health or regenerate recommendations because that would mix reporting with decision-making.

### Why the realtime system uses scheduling

For the hackathon, a simple scheduled refresh gives the feel of near real-time updates without introducing Kafka, WebSockets, Redis, or background worker complexity.

### Why alerts are persisted only

The backend records that something important changed. Delivery channels can be added later without changing the alert creation rule.

---

## JAVA_HOME Troubleshooting

If the Maven wrapper prints this error:

```text
The JAVA_HOME environment variable is not defined correctly
```

Install JDK 17, then point `JAVA_HOME` to the JDK folder.

Example Windows PowerShell session:

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

Permanent Windows setup:

1. Open `Edit the system environment variables`.
2. Open `Environment Variables`.
3. Add or update `JAVA_HOME`.
4. Set it to your JDK 17 folder, for example:

```text
C:\Program Files\Java\jdk-17
```

5. Add this to `Path` if it is not already present:

```text
%JAVA_HOME%\bin
```

6. Open a new terminal and verify:

```powershell
java -version
javac -version
.\mvnw.cmd -v
```

---

## Future Work

Good next steps:

- Add authentication and user ownership for positions.
- Add request/response tests for each controller.
- Add service-level unit tests for scoring and recommendations.
- Add database migrations with Flyway or Liquibase.
- Add OpenAPI documentation.
- Add a proper market data ingestion layer.
- Add frontend-facing pagination for alerts and history.
- Add retention rules for old health snapshots.
- Add notification delivery after the alert model settles.
- Normalize package names to lowercase across the early modules.

---

## Project Principle

Position Doctor is not trying to predict the market.

It is trying to make position review more disciplined:

- measure the position,
- explain the condition,
- surface changes,
- and give the user a clear dashboard to act from.

