export type Recommendation =
  | "HOLD"
  | "BUY_MORE"
  | "REDUCE"
  | "EXIT"
  | "WATCH"

export type HealthStatus = "HEALTHY" | "STABLE" | "AT_RISK" | "CRITICAL"

export interface Position {
  id: string
  stockName: string
  ticker: string
  quantity: number
  buyPrice: number
  currentPrice: number
  healthScore: number
  healthStatus: HealthStatus
  recommendation: Recommendation
  confidence: number
}

export interface NewPosition {
  stockName: string
  ticker: string
  quantity: number
  buyPrice: number
  targetPrice: number
  stopLoss: number
}

export type AlertSeverity = "INFO" | "WARNING" | "CRITICAL"

export interface Alert {
  id: string
  ticker: string
  message: string
  severity: AlertSeverity
  createdAt: string
}

export interface DigestItem {
  ticker: string
  stockName: string
  summary: string
  recommendation: Recommendation
}

export interface PortfolioDigest {
  date: string
  overview: string
  totalPositions: number
  healthyCount: number
  atRiskCount: number
  items: DigestItem[]
}
