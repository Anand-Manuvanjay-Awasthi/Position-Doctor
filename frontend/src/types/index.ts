export type PrimaryRecommendation =
  | "STRONG_HOLD"
  | "HOLD"
  | "WATCH_CLOSELY"
  | "REDUCE_POSITION"
  | "CONSIDER_EXIT"

export type SecondaryRecommendation =
  | "BOOK_PROFIT"
  | "TIGHTEN_STOP_LOSS"
  | "HEDGE_POSITION"

export type Recommendation = PrimaryRecommendation | SecondaryRecommendation

export type HealthStatus = "HEALTHY" | "WARNING" | "CRITICAL"

export type RiskLevel = "SAFE" | "WARNING" | "CRITICAL"

export type FluctuationLevel = "STABLE" | "MODERATE" | "VOLATILE"

export type FearGreedLevel =
  | "EXTREME_FEAR"
  | "FEAR"
  | "NEUTRAL"
  | "GREED"
  | "EXTREME_GREED"

export type StrengthLevel = "WEAK" | "MODERATE" | "STRONG"

export type Trend =
  | "STRONG_UPTREND"
  | "UPTREND"
  | "SIDEWAYS"
  | "DOWNTREND"
  | "STRONG_DOWNTREND"

export interface NewPosition {
  stockSymbol: string
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

export interface PositionResponse {
  id: number
  stockSymbol: string
  quantity: number
  buyPrice: number
  currentPrice: number
  stopLoss: number
  targetPrice: number
  createdAt: string
}

export interface PositionSummary {
  id: number
  stockSymbol: string
  healthScore: number | null
  primaryRecommendation: PrimaryRecommendation | null
  confidence: number | null
}

export interface PositionDetail {
  id: number
  stockSymbol: string
  quantity: number
  buyPrice: number
  currentPrice: number
  stopLoss: number
  targetPrice: number
  healthScore: number
  healthStatus: HealthStatus
  riskLevel: RiskLevel
  fluctuationLevel: FluctuationLevel
  riskScore: number
  performanceScore: number
  stabilityScore: number
  fearGreedIndex: number
  fearGreedLevel: FearGreedLevel
  eps: number
  roe: number
  fundamentalStrength: StrengthLevel
  primaryRecommendation: PrimaryRecommendation
  secondaryRecommendations: SecondaryRecommendation[]
  confidence: number
  rationale: string
  companyInsights: string
}

export interface AlertRow {
  id: number
  stockSymbol: string
  previousRecommendation: PrimaryRecommendation
  currentRecommendation: PrimaryRecommendation
  message: string
  createdAt: string
  isRead: boolean
}

export interface PortfolioDigestResponse {
  totalPositions: number
  healthyPositions: number
  watchCloselyPositions: number
  reducePositionRecommendations: number
  considerExitRecommendations: number
  averageHealthScore: number
  highestHealthPosition: string | null
  highestHealthScore: number | null
  lowestHealthPosition: string | null
  lowestHealthScore: number | null
  highestConfidencePosition: string | null
  highestConfidenceRecommendation: PrimaryRecommendation | null
  highestConfidence: number | null
  lowestConfidencePosition: string | null
  lowestConfidenceRecommendation: PrimaryRecommendation | null
  lowestConfidence: number | null
  unreadAlerts: number
}
