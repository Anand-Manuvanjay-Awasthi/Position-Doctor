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

export type Trend =
  | "STRONG_UPTREND"
  | "UPTREND"
  | "SIDEWAYS"
  | "DOWNTREND"
  | "STRONG_DOWNTREND"

export interface NewPosition {
  stockName: string
  quantity: number
  buyPrice: number
  currentPrice: number
  stopLoss: number
  targetPrice: number
  trend: Trend
  fearGreedIndex: number
  eps: number
  roe: number
}

export interface PositionDetail {
  id: string
  // Position information
  stockName: string
  ticker: string
  quantity: number
  buyPrice: number
  currentPrice: number
  stopLoss: number
  targetPrice: number
  // Health analysis
  healthScore: number
  riskScore: number
  performanceScore: number
  stabilityScore: number
  fluctuation: number
  // Market analysis
  marketContext: string
  fearGreedIndex: number
  // Fundamental analysis
  eps: number
  roe: number
  fundamentalStrength: string
  // Recommendation
  recommendation: Recommendation
  secondaryRecommendations: Recommendation[]
  confidence: number
  rationale: string
  companyInsights: string
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

export type AlertStatus = "READ" | "UNREAD"

export interface AlertRow {
  id: string
  symbol: string
  previousRecommendation: Recommendation
  currentRecommendation: Recommendation
  message: string
  timestamp: string
  status: AlertStatus
}

export interface DigestSummaryRow {
  stock: string
  ticker: string
  healthScore: number
  recommendation: Recommendation
  confidence: number
}
