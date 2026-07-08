import api from "./api"
import type { PortfolioDigestResponse } from "../types"

const BASE = "/api/v1/digest"

export async function getDigest(): Promise<PortfolioDigestResponse> {
  const res = await api.get<PortfolioDigestResponse>(BASE)
  return res.data
}
