import api from "./api"
import type { AlertRow, AlertStatus, Recommendation } from "../types"

/**
 * Alert service.
 *
 * Endpoints:
 *   GET   /api/v1/alerts            -> list alerts
 *   PATCH /api/v1/alerts/{id}/read  -> mark an alert as read
 *
 * The backend alert DTO is mapped to the `AlertRow` shape consumed by the
 * Alerts UI. Field access is defensive so common naming variations
 * (symbol/ticker, timestamp/createdAt, status/read) are all handled.
 */

const BASE = "/api/v1/alerts"

interface AlertResponse {
  id: string | number
  symbol?: string
  ticker?: string
  previousRecommendation?: Recommendation
  currentRecommendation?: Recommendation
  recommendation?: Recommendation
  message?: string
  timestamp?: string
  createdAt?: string
  status?: AlertStatus
  read?: boolean
}

function toAlertRow(a: AlertResponse): AlertRow {
  return {
    id: String(a.id),
    symbol: a.symbol ?? a.ticker ?? "",
    previousRecommendation: a.previousRecommendation ?? "HOLD",
    currentRecommendation:
      a.currentRecommendation ?? a.recommendation ?? "HOLD",
    message: a.message ?? "",
    timestamp: a.timestamp ?? a.createdAt ?? new Date().toISOString(),
    status: a.status ?? (a.read ? "READ" : "UNREAD"),
  }
}

export async function getAlerts(): Promise<AlertRow[]> {
  const res = await api.get<AlertResponse[]>(BASE)
  return (res.data ?? []).map(toAlertRow)
}

export async function markAlertAsRead(id: string): Promise<void> {
  await api.patch(`${BASE}/${id}/read`)
}
