import { useEffect, useState } from "react"
import DigestCard from "../components/DigestCard"
import { getDigest } from "../services/digestService"
import type { PortfolioDigestResponse } from "../types"

function formatScore(score: number | null) {
  return score === null ? "N/A" : score
}

function formatRecommendation(recommendation: string | null) {
  return recommendation === null ? "N/A" : recommendation.replace(/_/g, " ")
}

export default function DigestPage() {
  const [digest, setDigest] = useState<PortfolioDigestResponse | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let active = true
    setLoading(true)
    setError(null)

    getDigest()
      .then((data) => {
        if (active) setDigest(data)
      })
      .catch(() => {
        if (active) setError("Unable to load the digest. Please try again later.")
      })
      .finally(() => {
        if (active) setLoading(false)
      })

    return () => {
      active = false
    }
  }, [])

  return (
    <section>
      <h1 className="mb-1 text-2xl font-bold text-slate-900">
        Daily Portfolio Digest
      </h1>
      <p className="mb-6 text-sm text-slate-500">
        A summary of your portfolio health and current recommendations.
      </p>

      {loading && <p className="text-sm text-slate-500">Loading digest...</p>}
      {error && <p className="text-sm text-red-600">{error}</p>}
      {!loading && !error && !digest && (
        <p className="text-sm text-slate-500">
          No digest data available right now.
        </p>
      )}

      {!loading && !error && digest && (
        <>
          <div className="mb-8 grid grid-cols-2 gap-3 sm:grid-cols-4">
            <DigestCard label="Total Positions" value={digest.totalPositions} />
            <DigestCard
              label="Healthy Positions"
              value={digest.healthyPositions}
              tone="green"
            />
            <DigestCard
              label="Watch Closely"
              value={digest.watchCloselyPositions}
              tone="slate"
            />
            <DigestCard
              label="Reduce Position"
              value={digest.reducePositionRecommendations}
              tone="amber"
            />
            <DigestCard
              label="Consider Exit"
              value={digest.considerExitRecommendations}
              tone="red"
            />
            <DigestCard
              label="Average Health Score"
              value={Math.round(digest.averageHealthScore)}
            />
            <DigestCard
              label="Unread Alerts"
              value={digest.unreadAlerts}
              tone="amber"
            />
          </div>

          <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
            <DigestCard
              label={`Highest Health: ${digest.highestHealthPosition ?? "N/A"}`}
              value={formatScore(digest.highestHealthScore)}
              tone="green"
            />
            <DigestCard
              label={`Lowest Health: ${digest.lowestHealthPosition ?? "N/A"}`}
              value={formatScore(digest.lowestHealthScore)}
              tone="red"
            />
            <DigestCard
              label={`Highest Confidence: ${digest.highestConfidencePosition ?? "N/A"}`}
              value={
                digest.highestConfidence === null
                  ? "N/A"
                  : `${digest.highestConfidence}%`
              }
              tone="green"
            />
            <DigestCard
              label={formatRecommendation(digest.highestConfidenceRecommendation)}
              value="Top Signal"
            />
            <DigestCard
              label={`Lowest Confidence: ${digest.lowestConfidencePosition ?? "N/A"}`}
              value={
                digest.lowestConfidence === null
                  ? "N/A"
                  : `${digest.lowestConfidence}%`
              }
              tone="red"
            />
            <DigestCard
              label={formatRecommendation(digest.lowestConfidenceRecommendation)}
              value="Lowest Signal"
            />
          </div>
        </>
      )}
    </section>
  )
}
