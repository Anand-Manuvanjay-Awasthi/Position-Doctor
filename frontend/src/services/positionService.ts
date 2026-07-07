import axios from "axios"
import api from "./api"
import type { NewPosition, Position, PositionDetail } from "../types"

/**
 * Position service.
 *
 * All position-related business logic and API access lives here so that
 * React components never talk to Axios directly.
 *
 * Endpoints:
 *   GET    /api/v1/positions        -> list all positions
 *   GET    /api/v1/positions/{id}   -> full position detail (DTO)
 *   POST   /api/v1/positions        -> create a position
 *   PUT    /api/v1/positions/{id}   -> update a position
 *   DELETE /api/v1/positions/{id}   -> delete a position
 */

const BASE = "/api/v1/positions"

export async function getPositions(): Promise<Position[]> {
  const res = await api.get<Position[]>(BASE)
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
    const res = await api.get<PositionDetail>(`${BASE}/${id}`)
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
): Promise<Position> {
  const res = await api.post<Position>(BASE, position)
  return res.data
}

export async function updatePosition(
  id: string,
  position: NewPosition,
): Promise<Position> {
  const res = await api.put<Position>(`${BASE}/${id}`, position)
  return res.data
}

export async function deletePosition(id: string): Promise<void> {
  await api.delete(`${BASE}/${id}`)
}
