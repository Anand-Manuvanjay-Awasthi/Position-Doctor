import api from "./api"
import type { DigestSummaryRow, Recommendation } from "../types"

/**
 * Portfolio digest service.
 *
 * Endpoint:
 *   GET /api/v1/digest -> dashboard summary
 *
 * The response is mapped to the `DigestSummaryRow[]` shape consumed by the
 * Digest page. The endpoint may return either a bare array or an object
 * wrapping the rows under `items`/`positions`, and field names are accessed
 * defensively (stock/stockName, ticker/symbol).
 */

const BASE = "/api/v1/digest"

interface DigestItemResponse {
  stock?: string
  stockName?: string
  ticker?: string
  symbol?: string
  healthScore?: number
  recommendation?: Recommendation
  confidence?: number
}

interface DigestResponse {
  items?: DigestItemResponse[]
  positions?: DigestItemResponse[]
}

function toSummaryRow(item: DigestItemResponse): DigestSummaryRow {
  return {
    stock: item.stock ?? item.stockName ?? "",
    ticker: item.ticker ?? item.symbol ?? "",
    healthScore: item.healthScore ?? 0,
    recommendation: item.recommendation ?? "HOLD",
    confidence: item.confidence ?? 0,
  }
}

export async function getDigest(): Promise<DigestSummaryRow[]> {
  const res = await api.get<DigestResponse | DigestItemResponse[]>(BASE)
  const data = res.data
  const items = Array.isArray(data)
    ? data
    : (data?.items ?? data?.positions ?? [])
  return items.map(toSummaryRow)
}
