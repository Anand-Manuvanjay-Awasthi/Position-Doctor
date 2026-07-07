import type {
  Alert,
  NewPosition,
  PortfolioDigest,
  Position,
  PositionDetail,
} from "../types"

/**
 * Dummy data layer.
 *
 * These functions mimic async API calls but return static data so the
 * frontend is fully functional without a backend. Swap the bodies with
 * calls to the `api` axios instance when the backend is ready.
 */

const DUMMY_POSITIONS: Position[] = [
  {
    id: "1",
    stockName: "Apple Inc.",
    ticker: "AAPL",
    quantity: 25,
    buyPrice: 172.5,
    currentPrice: 214.3,
    healthScore: 88,
    healthStatus: "HEALTHY",
    recommendation: "HOLD",
    confidence: 0.91,
  },
  {
    id: "2",
    stockName: "Tesla Inc.",
    ticker: "TSLA",
    quantity: 15,
    buyPrice: 265.0,
    currentPrice: 238.7,
    healthScore: 54,
    healthStatus: "AT_RISK",
    recommendation: "REDUCE",
    confidence: 0.72,
  },
  {
    id: "3",
    stockName: "NVIDIA Corp.",
    ticker: "NVDA",
    quantity: 40,
    buyPrice: 98.2,
    currentPrice: 141.6,
    healthScore: 94,
    healthStatus: "HEALTHY",
    recommendation: "BUY_MORE",
    confidence: 0.86,
  },
  {
    id: "4",
    stockName: "Coinbase Global",
    ticker: "COIN",
    quantity: 10,
    buyPrice: 245.0,
    currentPrice: 178.4,
    healthScore: 32,
    healthStatus: "CRITICAL",
    recommendation: "EXIT",
    confidence: 0.79,
  },
  {
    id: "5",
    stockName: "Microsoft Corp.",
    ticker: "MSFT",
    quantity: 30,
    buyPrice: 402.1,
    currentPrice: 428.9,
    healthScore: 76,
    healthStatus: "STABLE",
    recommendation: "HOLD",
    confidence: 0.83,
  },
]

const DUMMY_ALERTS: Alert[] = [
  {
    id: "a1",
    ticker: "COIN",
    message: "Price dropped below stop-loss threshold.",
    severity: "CRITICAL",
    createdAt: "2026-07-07T09:14:00Z",
  },
  {
    id: "a2",
    ticker: "TSLA",
    message: "Position health degraded from STABLE to AT_RISK.",
    severity: "WARNING",
    createdAt: "2026-07-07T08:02:00Z",
  },
  {
    id: "a3",
    ticker: "NVDA",
    message: "Approaching target price. Consider taking partial profits.",
    severity: "INFO",
    createdAt: "2026-07-06T15:47:00Z",
  },
]

const DUMMY_DIGEST: PortfolioDigest = {
  date: "2026-07-07",
  overview:
    "Your portfolio is broadly healthy with strong momentum in tech holdings. One position requires immediate attention.",
  totalPositions: 5,
  healthyCount: 3,
  atRiskCount: 2,
  items: [
    {
      ticker: "NVDA",
      stockName: "NVIDIA Corp.",
      summary: "Strong fundamentals and upward trend. Momentum remains intact.",
      recommendation: "BUY_MORE",
    },
    {
      ticker: "COIN",
      stockName: "Coinbase Global",
      summary: "Broke stop-loss with weakening fundamentals. Risk is elevated.",
      recommendation: "EXIT",
    },
    {
      ticker: "TSLA",
      stockName: "Tesla Inc.",
      summary: "Volatility rising and P&L turned negative. Trim exposure.",
      recommendation: "REDUCE",
    },
  ],
}

const DUMMY_POSITION_DETAILS: Record<string, PositionDetail> = {
  "1": {
    id: "1",
    stockName: "Apple Inc.",
    ticker: "AAPL",
    quantity: 25,
    buyPrice: 172.5,
    currentPrice: 214.3,
    stopLoss: 160.0,
    targetPrice: 240.0,
    healthScore: 88,
    riskScore: 22,
    performanceScore: 91,
    stabilityScore: 84,
    fluctuation: 3.4,
    marketContext:
      "Broad market is in a moderate uptrend with technology leading gains. Sector rotation remains favorable for large-cap tech.",
    fearGreedIndex: 68,
    eps: 6.42,
    roe: 147.2,
    fundamentalStrength: "STRONG",
    recommendation: "HOLD",
    secondaryRecommendations: ["WATCH"],
    confidence: 0.91,
    rationale:
      "The position is well above its buy price with strong momentum and low volatility. Fundamentals are excellent and the stop-loss provides a comfortable downside buffer, so holding captures further upside without adding risk.",
    companyInsights:
      "Apple continues to generate industry-leading cash flow with growing services revenue offsetting slower hardware cycles. Margin expansion and aggressive buybacks support long-term shareholder value.",
  },
  "2": {
    id: "2",
    stockName: "Tesla Inc.",
    ticker: "TSLA",
    quantity: 15,
    buyPrice: 265.0,
    currentPrice: 238.7,
    stopLoss: 225.0,
    targetPrice: 300.0,
    healthScore: 54,
    riskScore: 61,
    performanceScore: 42,
    stabilityScore: 48,
    fluctuation: 7.9,
    marketContext:
      "Consumer discretionary is under pressure amid rate uncertainty. EV demand signals are mixed, adding to short-term volatility.",
    fearGreedIndex: 44,
    eps: 3.12,
    roe: 22.8,
    fundamentalStrength: "MODERATE",
    recommendation: "REDUCE",
    secondaryRecommendations: ["WATCH"],
    confidence: 0.72,
    rationale:
      "The position is underwater with elevated volatility and a weakening trend. Trimming exposure reduces downside risk while retaining a smaller stake in case sentiment recovers toward the target.",
    companyInsights:
      "Tesla maintains a leadership position in EVs but faces intensifying competition and margin compression from price cuts. Energy and autonomy remain long-term optionality rather than near-term catalysts.",
  },
  "3": {
    id: "3",
    stockName: "NVIDIA Corp.",
    ticker: "NVDA",
    quantity: 40,
    buyPrice: 98.2,
    currentPrice: 141.6,
    stopLoss: 120.0,
    targetPrice: 165.0,
    healthScore: 94,
    riskScore: 18,
    performanceScore: 97,
    stabilityScore: 88,
    fluctuation: 4.1,
    marketContext:
      "AI-driven demand keeps semiconductors in a strong uptrend. Institutional inflows remain robust across the sector.",
    fearGreedIndex: 72,
    eps: 2.95,
    roe: 115.4,
    fundamentalStrength: "STRONG",
    recommendation: "BUY_MORE",
    secondaryRecommendations: ["HOLD"],
    confidence: 0.86,
    rationale:
      "Exceptional performance and fundamentals combine with a strong uptrend and controlled volatility. Adding on strength is justified while the position stays comfortably above its stop-loss.",
    companyInsights:
      "NVIDIA dominates the accelerated-computing market with deep software moats around CUDA. Data-center demand continues to outpace supply, sustaining premium pricing power.",
  },
  "4": {
    id: "4",
    stockName: "Coinbase Global",
    ticker: "COIN",
    quantity: 10,
    buyPrice: 245.0,
    currentPrice: 178.4,
    stopLoss: 190.0,
    targetPrice: 280.0,
    healthScore: 32,
    riskScore: 84,
    performanceScore: 24,
    stabilityScore: 29,
    fluctuation: 11.6,
    marketContext:
      "Crypto markets are in a pronounced downtrend with risk-off sentiment. Trading volumes and volatility are elevated.",
    fearGreedIndex: 28,
    eps: -1.45,
    roe: -8.3,
    fundamentalStrength: "WEAK",
    recommendation: "EXIT",
    secondaryRecommendations: ["REDUCE"],
    confidence: 0.79,
    rationale:
      "The position has breached its stop-loss with deteriorating fundamentals and extreme volatility. Exiting preserves capital and avoids further drawdown in a hostile market environment.",
    companyInsights:
      "Coinbase revenue is highly correlated to crypto trading activity, which has contracted sharply. Regulatory overhang and negative earnings weigh on the near-term outlook.",
  },
  "5": {
    id: "5",
    stockName: "Microsoft Corp.",
    ticker: "MSFT",
    quantity: 30,
    buyPrice: 402.1,
    currentPrice: 428.9,
    stopLoss: 385.0,
    targetPrice: 470.0,
    healthScore: 76,
    riskScore: 31,
    performanceScore: 74,
    stabilityScore: 82,
    fluctuation: 2.8,
    marketContext:
      "Large-cap software remains resilient with steady institutional demand. The broad market trend is neutral-to-positive.",
    fearGreedIndex: 61,
    eps: 11.8,
    roe: 39.1,
    fundamentalStrength: "STRONG",
    recommendation: "HOLD",
    secondaryRecommendations: ["BUY_MORE"],
    confidence: 0.83,
    rationale:
      "A profitable position with low volatility and strong fundamentals supports holding. The trend is stable and the position sits well above its stop-loss with room toward the target.",
    companyInsights:
      "Microsoft benefits from durable cloud growth via Azure and rapid monetization of AI across its product suite. Diversified recurring revenue underpins predictable earnings.",
  },
}

function delay<T>(value: T, ms = 300): Promise<T> {
  return new Promise((resolve) => setTimeout(() => resolve(value), ms))
}

export function getPositions(): Promise<Position[]> {
  // return api.get<Position[]>("/positions").then((res) => res.data)
  return delay(DUMMY_POSITIONS)
}

export function getPositionById(id: string): Promise<Position | undefined> {
  return delay(DUMMY_POSITIONS.find((p) => p.id === id))
}

export function getPositionDetailById(
  id: string,
): Promise<PositionDetail | undefined> {
  // return api.get<PositionDetail>(`/positions/${id}`).then((res) => res.data)
  return delay(DUMMY_POSITION_DETAILS[id])
}

export function createPosition(position: NewPosition): Promise<NewPosition> {
  // return api.post<NewPosition>("/positions", position).then((res) => res.data)
  return delay(position)
}

export function getAlerts(): Promise<Alert[]> {
  return delay(DUMMY_ALERTS)
}

export function getDigest(): Promise<PortfolioDigest> {
  return delay(DUMMY_DIGEST)
}
