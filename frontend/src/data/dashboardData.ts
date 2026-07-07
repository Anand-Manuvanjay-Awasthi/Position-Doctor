import type { AlertRow, DigestSummaryRow } from "../types"

export const alertRows: AlertRow[] = [
  {
    id: "a1",
    symbol: "TCS",
    previousRecommendation: "HOLD",
    currentRecommendation: "REDUCE",
    message: "Health score dropped below 60 after weak quarterly guidance.",
    timestamp: "2025-07-07T09:15:00",
    status: "UNREAD",
  },
  {
    id: "a2",
    symbol: "INFY",
    previousRecommendation: "BUY_MORE",
    currentRecommendation: "HOLD",
    message: "Momentum cooling off; trend shifted from uptrend to sideways.",
    timestamp: "2025-07-07T08:42:00",
    status: "UNREAD",
  },
  {
    id: "a3",
    symbol: "RELIANCE",
    previousRecommendation: "HOLD",
    currentRecommendation: "BUY_MORE",
    message: "Fundamentals strengthened; ROE improved for third straight quarter.",
    timestamp: "2025-07-06T16:05:00",
    status: "READ",
  },
  {
    id: "a4",
    symbol: "HDFCBANK",
    previousRecommendation: "WATCH",
    currentRecommendation: "EXIT",
    message: "Price broke below stop-loss with rising downside risk.",
    timestamp: "2025-07-06T11:30:00",
    status: "UNREAD",
  },
  {
    id: "a5",
    symbol: "WIPRO",
    previousRecommendation: "REDUCE",
    currentRecommendation: "WATCH",
    message: "Stabilizing near support; risk score improved slightly.",
    timestamp: "2025-07-05T14:20:00",
    status: "READ",
  },
]

export const digestSummaryRows: DigestSummaryRow[] = [
  {
    stock: "Reliance Industries",
    ticker: "RELIANCE",
    healthScore: 88,
    recommendation: "BUY_MORE",
    confidence: 0.91,
  },
  {
    stock: "Tata Consultancy Services",
    ticker: "TCS",
    healthScore: 58,
    recommendation: "REDUCE",
    confidence: 0.74,
  },
  {
    stock: "Infosys",
    ticker: "INFY",
    healthScore: 71,
    recommendation: "HOLD",
    confidence: 0.68,
  },
  {
    stock: "HDFC Bank",
    ticker: "HDFCBANK",
    healthScore: 42,
    recommendation: "EXIT",
    confidence: 0.82,
  },
  {
    stock: "Wipro",
    ticker: "WIPRO",
    healthScore: 64,
    recommendation: "WATCH",
    confidence: 0.6,
  },
  {
    stock: "Larsen & Toubro",
    ticker: "LT",
    healthScore: 79,
    recommendation: "HOLD",
    confidence: 0.77,
  },
]
