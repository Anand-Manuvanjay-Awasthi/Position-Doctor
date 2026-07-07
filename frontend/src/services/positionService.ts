import type {
  Alert,
  NewPosition,
  PortfolioDigest,
  Position,
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
