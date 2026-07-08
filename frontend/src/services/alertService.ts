import api from "./api"
import type { AlertRow, PrimaryRecommendation } from "../types"

/**
 * Alert service.
 *
 * Endpoints:
 *   GET   /api/v1/alerts            -> list alerts
 *   PATCH /api/v1/alerts/{id}/read  -> mark an alert as read
 *
 * The backend alert DTO is mapped to the `AlertRow` shape consumed by the
 * Alerts UI.
 */

const BASE = "/api/v1/alerts"

interface AlertResponse {
  id: number
  stockSymbol: string
  previousRecommendation: PrimaryRecommendation
  currentRecommendation: PrimaryRecommendation
  message: string
  createdAt: string
  isRead: boolean
}

function toAlertRow(a: AlertResponse): AlertRow {
  return {
    id: a.id,
    stockSymbol: a.stockSymbol,
    previousRecommendation: a.previousRecommendation,
    currentRecommendation: a.currentRecommendation,
    message: a.message,
    createdAt: a.createdAt,
    isRead: a.isRead,
  }
}

export async function getAlerts(): Promise<AlertRow[]> {
  const res = await api.get<AlertResponse[]>(BASE)
  return (res.data ?? []).map(toAlertRow)
}

export async function markAlertAsRead(id: number): Promise<void> {
  await api.patch(`${BASE}/${id}/read`)
}
