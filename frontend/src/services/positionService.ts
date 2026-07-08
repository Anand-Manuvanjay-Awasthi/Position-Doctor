import axios from "axios"
import api from "./api"
import type {
  NewPosition,
  PositionDetail,
  PositionResponse,
  PositionSummary,
} from "../types"

/**
 * Position service.
 *
 * All position-related business logic and API access lives here so that
 * React components never talk to Axios directly.
 *
 * Endpoints:
 *   GET    /api/v1/positions/summaries      -> portfolio table rows
 *   GET    /api/v1/positions/{id}/details   -> full position detail
 *   POST   /api/v1/positions        -> create a position
 *   DELETE /api/v1/positions/{id}   -> delete a position
 */

const BASE = "/api/v1/positions"

export async function getPositions(): Promise<PositionSummary[]> {
  const res = await api.get<PositionSummary[]>(`${BASE}/summaries`)
  return res.data ?? []
}

/**
 * Fetches the full detail DTO for a single position. Returns `undefined`
 * for a 404 so the UI can render its "not found" empty state instead of
 * treating a missing record as a hard error.
 */
export async function getPositionDetailById(
  id: string,
): Promise<PositionDetail | undefined> {
  try {
    const res = await api.get<PositionDetail>(`${BASE}/${id}/details`)
    return res.data
  } catch (err) {
    if (axios.isAxiosError(err) && err.response?.status === 404) {
      return undefined
    }
    throw err
  }
}

export async function createPosition(
  position: NewPosition,
): Promise<PositionResponse> {
  const res = await api.post<PositionResponse>(BASE, position)
  return res.data
}

export async function deletePosition(id: string): Promise<void> {
  await api.delete(`${BASE}/${id}`)
}
