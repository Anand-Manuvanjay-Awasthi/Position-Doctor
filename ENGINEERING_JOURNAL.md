# Position Doctor — Engineering Journal

A log of architecture decisions, tradeoffs, and lessons learned while building Position Doctor.

---

## Table of Contents

1. [Health Score Architecture](#decision-1--health-score-architecture)
2. [Risk Should Dominate Performance](#decision-2--risk-should-dominate-performance)
3. [Volatility Should Not Hard Cap Health](#decision-3--volatility-should-not-hard-cap-health)
4. [Health History Storage](#decision-4--health-history-storage)
5. [Scheduled Monitoring](#decision-5--scheduled-monitoring)
6. [Snapshot Storage Strategy](#decision-6--snapshot-storage-strategy)
7. [Production Endpoints vs Sandbox Endpoints](#decision-7--production-endpoints-vs-sandbox-endpoints)
8. [Market Context as a Separate Intelligence Layer](#decision-8--market-context-as-a-separate-intelligence-layer)
9. [Fear & Greed as the Only Market Context Indicator](#decision-9--fear--greed-as-the-only-market-context-indicator)
10. [Fundamental Strength as an Independent Layer](#decision-10--fundamental-strength-as-an-independent-layer)
11. [Limiting Fundamentals to EPS and ROE](#decision-11--limiting-fundamentals-to-eps-and-roe)
12. [Recommendation Engine Architecture](#decision-12--recommendation-engine-architecture)
13. [Primary Actions vs Secondary Actions](#decision-13--primary-actions-vs-secondary-actions)
14. [Role of Fundamental Strength](#decision-14--role-of-fundamental-strength)
15. [Handling Contradictory Signals](#decision-15--handling-contradictory-signals)
16. [Confidence Score Design](#decision-16--confidence-score-design)
17. [Layer Responsibilities](#decision-17--layer-responsibilities)
18. [Plain Language Rationales](#decision-18--plain-language-rationales)
19. [Realtime Update Engine](#decision-19--realtime-update-engine)
20. [Alert Engine](#decision-20--alert-engine)
21. [Daily Position Health Digest](#decision-21--daily-position-health-digest)

---

## Decision #1 — Health Score Architecture

### Question
Should Health Score be a single weighted formula containing every indicator?

### Considered Factors
- P&L
- Trend
- Stop Loss Distance
- Target Distance
- Volatility
- Fear & Greed Index
- EPS
- PE Ratio

### Problem
Not all indicators answer the same question. Position metrics, market sentiment metrics, and company fundamentals belong to different domains. Combining them into one score creates a black-box system that is difficult to explain.

### Decision
Separate the system into:
1. Position Health Layer
2. Market Context Layer
3. Fundamental Strength Layer

### Reason
Each layer answers a different question.

| Layer | Question |
|---|---|
| Position Health | "How healthy is this trade?" |
| Market Context | "How favorable is the market environment?" |
| Fundamental Strength | "How strong is the company itself?" |

### Outcome
Improved explainability and easier future expansion.

---

## Decision #2 — Risk Should Dominate Performance

### Question
Should all factors contribute equally?

### Problem
A position near stop-loss could still receive a high score due to strong profits.

**Example:**
```
Risk        = 10/40
Performance = 40/40
Stability   = 20/20
Total       = 70
```
This incorrectly suggests a healthy position.

### Decision
Introduce hard constraints.

```java
if (riskScore < 20)
    health = min(health, 50);

if (performanceScore < 20)
    health = min(health, 60);
```

### Outcome
Dangerous positions can no longer appear healthy.

---

## Decision #3 — Volatility Should Not Hard Cap Health

### Question
Should poor stability reduce health aggressively?

### Problem
High volatility does not necessarily imply a bad position.

**Example:** Tesla or NVIDIA may be volatile while remaining profitable.

### Decision
Create a `FluctuationLevel` enum:
- `STABLE`
- `MODERATE`
- `VOLATILE`

### Outcome
Volatility communicates uncertainty without incorrectly penalizing healthy positions.

---

## Decision #4 — Health History Storage

### Question
Should all historical health scores be stored?

### Initial Thought
Store only the previous value.

### Problem
Loses long-term trend information.

**Example:**
```
80 → 75 → 70 → 65 → 60   becomes   65 → 60
```
Most context disappears.

### Current Decision
Store historical snapshots with retention limits.

### Future Improvement
Introduce a downsampling strategy similar to monitoring systems.

### Concepts Learned
- Monitoring Systems
- Time Series Data
- Data Retention
- Downsampling

---

## Decision #5 — Scheduled Monitoring

### Question
Should `@Scheduled` recalculate every position?

### Initial Thought
Use `@Async` to improve performance.

### Observation
Async does not eliminate work — it only changes where the work executes.

### Future Production Architecture
```
Price Update Event
        ↓
      Kafka
        ↓
Health Evaluation Workers
        ↓
   Alert Engine
```

### Concepts Learned
- Event Driven Architecture
- Background Workers
- Distributed Processing
- Async vs Scalability

---

## Decision #6 — Snapshot Storage Strategy

### Question
Should every health calculation be stored as a historical snapshot?

### Initial Thought
Store every calculated health score.

**Example:**
```
09:00 -> 82
09:02 -> 82
09:04 -> 82
09:06 -> 82
09:08 -> 82
```

### Problem
Most of these records contain identical information. This creates unnecessary database growth while providing little additional value to users. Users care about meaningful changes rather than every recalculation.

### Alternative Considered
Store only the previous health score.

**Problem:** This removes long-term trend visibility.

```
Original Trend: 80 → 75 → 70 → 65 → 60
Stored Values:  65 → 60
```
Most historical context is lost.

### Learning
There is a difference between:
1. **Raw Measurements** — `82 → 82 → 82 → 82 → 82`
2. **Meaningful Events** — `82 → 75 → 60 → 45`

Monitoring systems often store raw metrics. Business systems often store meaningful state transitions.

### Current Direction
Store a new snapshot only when:
- Health Score changes significantly
- Health Status changes
- Risk Level changes
- Fluctuation Level changes

**Example:**
```
Instead of:          Store:
82                    82
82                    81
82                    80
82
81
81
81
80
```

### Concepts Learned
- Snapshot Storage
- Event-Based Storage
- Change Detection
- Delta Storage
- Monitoring Systems vs Business Systems
- Data Retention Tradeoffs

### Future Improvements
- Retention Policies
- Snapshot Downsampling
- Time-Series Aggregation
- Historical Trend Compression

---

## Decision #7 — Production Endpoints vs Sandbox Endpoints

### Question
Why create two endpoints for the same functionality?

**Example:**
```
Production Endpoint:  GET  /api/v1/health/{positionId}
Sandbox Endpoint:     POST /api/v1/health/evaluate
```
Both eventually return a `PositionHealthReport`. At first glance this appears redundant.

### Initial Assumption
One endpoint should be enough — the production endpoint already exists.

### Observation
The production endpoint depends on the entire application flow:

```
Position ID
    ↓
Database Lookup
    ↓
Position Entity
    ↓
Health Engine
    ↓
Response
```

This means testing a new scoring rule requires:
1. Creating a position
2. Storing it in PostgreSQL
3. Retrieving it again
4. Calling the endpoint

The feedback cycle becomes slower.

### Sandbox Endpoint Concept
A sandbox endpoint exists purely for experimentation and testing.

**Example:**
```
POST /api/v1/health/evaluate
```
```json
{
  "buyPrice": 100,
  "currentPrice": 120,
  "targetPrice": 130,
  "stopLoss": 90
}
```

No database interaction. No persistence. No position creation. The endpoint directly invokes the Health Engine.

### Benefits
- **Faster Testing** — different values can be tested immediately (e.g. `currentPrice = 95, 120, 150`) without modifying database records.
- **Easier Debugging** — the Health Engine can be tested in isolation, helping identify whether issues originate from the Business Logic, Database Layer, or Controller Layer.
- **Better Frontend Development** — frontend developers can test scoring behavior without requiring stored positions. Useful for simulators, preview screens, and what-if analysis.
- **Improved Developer Experience** — reduces development friction and allows rapid experimentation while business rules evolve.

### Learning
Production APIs and Development APIs often have different goals.

| Endpoint | Purpose |
|---|---|
| Production | Serves actual application workflows |
| Sandbox | Serves developers and testing workflows |

Both can coexist without violating clean architecture.

### Real-World Examples
Many mature systems expose Internal Testing APIs, Preview APIs, Validation APIs, and Simulation APIs alongside production APIs — e.g. payment gateways, trading systems, recommendation engines, risk analysis platforms.

### Decision
Maintain both:
```
GET  /api/v1/health/{positionId}
POST /api/v1/health/evaluate
```
The production endpoint represents real usage. The sandbox endpoint accelerates development, testing, and experimentation.

### Concepts Learned
- Sandbox Endpoints
- Production APIs
- Rapid Feedback Loops
- Isolated Testing
- Developer Experience (DX)
- API Design Tradeoffs

---

## Decision #8 — Market Context as a Separate Intelligence Layer

### Question
Should market sentiment be directly merged into the Health Score Engine?

### Initial Thought
Include market sentiment indicators directly inside the Health Score calculation.

### Problem
Position health and market sentiment answer fundamentally different questions.

| Layer | Question |
|---|---|
| Position Health | "How healthy is this specific position?" |
| Market Context | "How favorable is the overall market environment?" |

Combining both into a single score would make the system harder to explain and maintain.

### Decision
Create a dedicated Market Context Layer.

```
Position Health Layer
        ↓
Market Context Layer
        ↓
Fundamental Strength Layer
        ↓
Recommendation Engine
```

### Benefits
- Clear separation of responsibilities
- Easier testing
- Easier future expansion
- More explainable recommendations
- Independent evolution of each layer

### Learning
A good architecture separates questions before it separates code.

### Concepts Learned
- Separation of Concerns
- Layered Architecture
- Explainable Systems
- Domain Modeling
- Decision Pipelines

---

## Decision #9 — Fear & Greed as the Only Market Context Indicator

### Question
Should additional market indicators be added?

**Indicators considered:**
- Fear & Greed Index
- VIX
- Market Breadth
- Sector Strength
- Put/Call Ratio
- Momentum Indicators

### Observation
The project requirements only require market context, not a complete trading system. Adding multiple indicators increases complexity while providing limited additional value for a hackathon.

### Decision
Use Fear & Greed Index as the sole Market Context signal in Version 1.

### Reasoning
Fear & Greed:
- Is easy to explain
- Represents market psychology
- Is widely recognized
- Produces meaningful recommendation inputs
- Has a high signal-to-complexity ratio

### Future Improvements
- VIX
- Sector Momentum
- Market Breadth
- Market Trend
- Put/Call Ratio

### Learning
Not every useful feature belongs in Version 1. The goal is not to maximize indicators — the goal is to maximize useful decision-making.

### Concepts Learned
- MVP Design
- Signal vs Noise
- Product Scope Management
- Feature Prioritization

### Market Context Layer — Logic

**Fear & Greed Classification**

| Score Range | Classification |
|---|---|
| 0 – 20 | `EXTREME_FEAR` |
| 21 – 40 | `FEAR` |
| 41 – 60 | `NEUTRAL` |
| 61 – 80 | `GREED` |
| 81 – 100 | `EXTREME_GREED` |

**Purpose:** Convert raw numerical sentiment values into meaningful categories that can be consumed by the Recommendation Engine.

**Examples:**
```
Fear & Greed = 15  →  EXTREME_FEAR
"Market participants appear highly risk-averse and defensive."

Fear & Greed = 82  →  EXTREME_GREED
"Market participants appear highly optimistic and risk-seeking."
```

**Design Principle:** Market Context should provide information. Recommendation Engine should make decisions. This separation keeps both systems simple and maintainable.

---

## Decision #10 — Fundamental Strength as an Independent Layer

### Question
Should company fundamentals be merged into the Health Score Engine?

### Observation
Position Health and Company Fundamentals answer different questions.

| Layer | Question |
|---|---|
| Position Health | "How healthy is this trade?" |
| Fundamental Strength | "How healthy is this company?" |

A position may be healthy even when the underlying company is weak. Similarly, a strong company may temporarily have an unhealthy position due to market conditions.

### Decision
Create a dedicated Fundamental Strength Layer.

```
Position Health Layer
        ↓
Market Context Layer
        ↓
Fundamental Strength Layer
        ↓
Recommendation Engine
```

### Benefits
- Clear separation of concerns
- Easier testing
- Easier future expansion
- Improved explainability
- Cleaner recommendation logic

### Learning
Different business questions should be modeled as different systems.

---

## Decision #11 — Limiting Fundamentals to EPS and ROE

### Question
Which fundamental indicators should be included?

**Indicators considered:**
- EPS
- ROE
- PE Ratio
- Revenue Growth
- Debt Ratio
- Cash Flow Metrics

### Observation
The project requirements do not require deep financial analysis. Adding more indicators increases complexity without significantly improving recommendation quality for the hackathon version.

### Decision
Use only:
- **EPS** (Earnings Per Share)
- **ROE** (Return On Equity)

### Reasoning
| Metric | Question |
|---|---|
| EPS | "Is the company generating earnings?" |
| ROE | "How efficiently is management using shareholder capital?" |

Together they provide a simple and explainable view of company strength.

### Future Improvements
- PE Ratio
- Revenue Growth
- Debt-to-Equity Ratio
- Operating Margin
- Cash Flow Analysis

### Learning
A good MVP focuses on the highest-value signals rather than maximizing the number of metrics.

### Concepts Learned
- Fundamental Analysis
- MVP Design
- Signal-to-Complexity Ratio
- Feature Prioritization
- Domain Modeling

### Fundamental Strength Layer — Logic

**Inputs:** EPS, ROE

**Strength Score:**
- EPS Contribution: 0 – 50
- ROE Contribution: 0 – 50
- Total Score: 0 – 100

**Strength Classification:**

| Score Range | Classification |
|---|---|
| 0 – 39 | `WEAK` |
| 40 – 69 | `MODERATE` |
| 70 – 100 | `STRONG` |

**Purpose:** Provide a simplified assessment of company quality that can later be consumed by the Recommendation Engine.

**Design Principle:** Fundamental Layer provides information. Recommendation Engine makes decisions.

---

## Decision #12 — Recommendation Engine Architecture

### Question
How should recommendations be generated?

### Approach 1 — Recommendation Score
Combine Health Score, Market Context, and Fundamental Strength into a single weighted recommendation score.

**Example:**
```
Health         = 60%
Market Context = 20%
Fundamentals   = 20%
Final Score → Recommendation
```

**Problems:**
- Difficult to explain
- Difficult to debug
- Difficult to justify recommendation decisions
- Similar problems were already observed in the Health Score Engine

### Approach 2 — Rule Engine
Generate recommendations using explicit business rules.

**Example:**
```
IF Health Score < 40
THEN CONSIDER_EXIT
```

### Decision
Use a Rule-Based Recommendation Engine.

### Reasoning
Rule-based systems are:
- More explainable
- Easier to debug
- Easier to extend
- Easier to demonstrate during judging
- Easier to justify to users

### Learning
The Recommendation Engine should consume facts and apply rules. It should not behave as another scoring engine.

### Concepts Learned
- Rule Engines
- Decision Systems
- Explainable Recommendations
- Business Rules
- Decision Pipelines

---

## Decision #13 — Primary Actions vs Secondary Actions

### Observation
Some recommendations represent the primary decision. Others are supporting actions.

**Example:** `BOOK_PROFIT` and `TIGHTEN_STOP_LOSS` can both be valid simultaneously.

### Decision
Split recommendations into Primary Actions and Secondary Actions.

**Primary Actions** (only one should be returned):
- `STRONG_HOLD`
- `HOLD`
- `WATCH_CLOSELY`
- `REDUCE_POSITION`
- `CONSIDER_EXIT`

**Secondary Actions** (multiple may be returned):
- `BOOK_PROFIT`
- `TIGHTEN_STOP_LOSS`
- `HEDGE_POSITION`

**Example:**
```
Primary Action:    HOLD
Secondary Actions: BOOK_PROFIT, TIGHTEN_STOP_LOSS
```

### Learning
Not all recommendations are mutually exclusive. Some recommendations enhance or support the primary recommendation.

### Concepts Learned
- Decision Hierarchies
- Recommendation Prioritization
- Action Classification
- Rule-Based Decision Making

---

## Decision #14 — Role of Fundamental Strength

### Question
Should Fundamental Strength directly determine recommendations?

### Observation
Fundamental Strength answers "How healthy is the company?" — it does not answer "What should be done right now?" Current position health remains the strongest indicator for immediate action.

### Decision
Fundamental Strength will act as a **Confidence Modifier**. It will not directly generate recommendations.

**Examples:**
```
Health = 90, Market = NEUTRAL, Fundamentals = STRONG
→ Recommendation: STRONG_HOLD | Confidence: HIGH

Health = 90, Market = NEUTRAL, Fundamentals = WEAK
→ Recommendation: HOLD | Confidence: MODERATE
```

### Learning
Company quality should influence confidence rather than dominate immediate trading actions.

### Concepts Learned
- Confidence Modifiers
- Decision Support Systems
- Signal Weighting
- Recommendation Confidence

---

## Decision #15 — Handling Contradictory Signals

### Question
What happens when different intelligence layers disagree?

**Examples:**
- Strong Position + Weak Fundamentals
- Strong Position + Extreme Fear
- Weak Position + Strong Fundamentals
- Healthy Position + Overheated Market

### Observation
Financial systems frequently produce conflicting signals. A company may be fundamentally strong while the current position is deteriorating. A position may be healthy while market sentiment is highly fearful. A recommendation system must be able to reason about these situations.

### Decision
The Recommendation Engine will evaluate signals independently rather than attempting to merge all signals into a single score.

```
Health Layer              →  Primary Recommendation
Market Context Layer      →  Secondary Recommendation
Fundamental Strength Layer→  Company Quality Indicator
```

### Benefits
- Easier to explain recommendations
- Easier to debug recommendation logic
- Handles conflicting signals naturally
- Prevents over-reliance on any single metric

### Learning
Real-world decision systems rarely operate on perfectly aligned inputs. Good recommendation systems must handle contradictions rather than ignore them.

---

## Decision #16 — Confidence Score Design

### Question
How should recommendation confidence be calculated?

### Initial Thought
Use Fundamental Strength to generate confidence.

### Observation
Fundamental Strength answers "How strong is the company?" Confidence answers "How strongly does the system believe the recommendation is appropriate right now?" These are different questions.

### Decision
Recommendation confidence will be derived from Health Score and Market Context. Fundamental Strength will remain an independent indicator.

### Confidence Calculation
**Base Confidence:** Health Score

**Market Modifiers:**

| Market Context | Modifier |
|---|---|
| `EXTREME_FEAR` | -20 |
| `FEAR` | -10 |
| `NEUTRAL` | 0 |
| `GREED` | +5 |
| `EXTREME_GREED` | -5 |

**Final Confidence:** Clamp between 0 and 100.

**Example:**
```
Health = 90, Market = FEAR
Confidence = 90 - 10 = 80
```

### Learning
Confidence should reflect the certainty of the recommendation rather than the quality of the company.

---

## Decision #17 — Layer Responsibilities

### Final Recommendation Architecture

**Health Layer**
- Responsibility: Determine the primary action.
- Possible Outputs: `STRONG_HOLD`, `HOLD`, `WATCH_CLOSELY`, `REDUCE_POSITION`, `CONSIDER_EXIT`

**Market Context Layer**
- Responsibility: Generate secondary recommendations.
- Possible Outputs: `BOOK_PROFIT`, `TIGHTEN_STOP_LOSS`, `HEDGE_POSITION`

**Fundamental Strength Layer**
- Responsibility: Provide company quality assessment.
- Possible Outputs: `STRONG`, `MODERATE`, `WEAK`
- This information is displayed alongside recommendations but does not directly generate actions.

### Learning
Each layer should answer a single question and own a single responsibility. This improves maintainability, explainability, and extensibility.

---

## Decision #18 — Plain Language Rationales

### Question
Should recommendation explanations be generated by AI or through deterministic templates?

### Approach 1 — AI-Based Explanation Generation
**Advantages:** Personalized responses, more natural language, highly flexible.
**Disadvantages:** Non-deterministic, increased latency, additional infrastructure, harder to test, overkill for hackathon scope.

### Approach 2 — Deterministic Template-Based Generation
**Advantages:** Predictable output, easy testing, fast execution, easy maintenance, completely explainable.

### Decision
Use deterministic switch-case based rationale generation.

```
Recommendation Engine
        ↓
Recommendation Explanation Generator
        ↓
Recommendation Response
```

The Recommendation Engine determines the action. The Explanation Generator only explains the action.

### Additional Design Decision
Recommendation explanations and company insights remain separate.

| Concept | Question |
|---|---|
| Recommendation Rationale | "Why did the system suggest this action?" |
| Company Insights | "What is the financial condition of the company?" |

Keeping both independent improves readability and separation of concerns.

### Learning
Decision making and explanation generation are separate responsibilities and should remain independent modules.

---

## Decision #19 — Realtime Update Engine

### Question
How should the system provide near real-time portfolio monitoring?

### Observation
Continuously recalculating every request would be inefficient. The backend should proactively refresh portfolio intelligence at fixed intervals.

### Decision
Introduce a scheduled background task using Spring's `@Scheduled`.

### Execution Flow
```
Scheduler
    ↓
Fetch Active Positions
    ↓
Recalculate Health Score
    ↓
Generate Recommendation
    ↓
Generate Plain Language Rationales
    ↓
Persist Health Snapshot
    ↓
Generate Alerts (if recommendation changes)
```

### Design Principles
- Scheduler performs orchestration only.
- Business logic remains inside dedicated services.
- Existing services are reused.
- No duplicate calculations.

### Future Scalability
For production deployment this scheduler can later be replaced by:
- Kafka Consumers
- Distributed Workers
- Quartz Scheduler
- Event Driven Processing

...without changing business logic.

### Learning
Schedulers should coordinate services rather than contain business logic.

---

## Decision #20 — Alert Engine

### Question
How should users be informed about meaningful portfolio changes?

### Observation
Only significant changes should generate alerts. Repeated alerts for identical recommendations reduce usefulness.

### Decision
Generate alerts only when the Primary Recommendation changes.

**Example (alert generated):**
```
HOLD → WATCH_CLOSELY  →  Alert Generated
```

**Example (no alert):**
```
WATCH_CLOSELY → WATCH_CLOSELY  →  No Alert
```

### Architecture
```
Realtime Scheduler
        ↓
Recommendation Engine
        ↓
Recommendation Comparison
        ↓
   Alert Service
        ↓
     Database
```

### Scope
The Alert Engine only stores alerts. Notification delivery (Email, SMS, Push Notifications) is intentionally excluded from the hackathon scope.

### Learning
A useful alert system detects meaningful changes rather than simply reporting every update.

---

## Decision #21 — Daily Position Health Digest

### Question
How should users obtain a quick overview of their portfolio without inspecting every individual position?

### Decision
Introduce a Daily Position Health Digest. The digest acts as an aggregation layer rather than another analytics engine.

### Information Included
- Total Positions
- Healthy Positions
- Watch Closely Positions
- Reduce Position Recommendations
- Consider Exit Recommendations
- Average Portfolio Health
- Highest Health Position
- Lowest Health Position
- Highest Confidence Recommendation
- Lowest Confidence Recommendation
- Unread Alerts Count

### Design Principle
The digest does not perform new calculations. It simply aggregates existing data produced by the Health Engine and Recommendation Engine.

### Benefits
- Faster dashboard rendering
- Improved portfolio visibility
- Cleaner frontend implementation
- Better user experience

### Learning
Aggregation services should reuse existing business logic instead of recalculating domain data.
