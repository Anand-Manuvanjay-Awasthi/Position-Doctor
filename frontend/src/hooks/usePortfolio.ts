import { getAlerts } from "../services/alertService"
import { getDigest } from "../services/digestService"
import { getPositions } from "../services/positionService"
import { useAsync } from "./useAsync"

export function usePositions() {
  return useAsync(getPositions, [])
}

export function useAlerts() {
  return useAsync(getAlerts, [])
}

export function useDigest() {
  return useAsync(getDigest, [])
}
