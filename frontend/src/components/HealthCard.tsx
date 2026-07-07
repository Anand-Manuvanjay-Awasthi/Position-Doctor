import type { PositionDetail } from "../types"
import SectionCard, { InfoRow } from "./SectionCard"

function scoreColor(score: number) {
  if (score >= 75) return "text-green-700"
  if (score >= 50) return "text-amber-700"
  return "text-red-700"
}

// Risk is inverted: a low risk score is good.
function riskColor(score: number) {
  if (score <= 33) return "text-green-700"
  if (score <= 66) return "text-amber-700"
  return "text-red-700"
}

export default function HealthCard({
  position,
}: {
  position: PositionDetail
}) {
  return (
    <SectionCard title="Health Analysis">
      <InfoRow
        label="Health Score"
        value={
          <span className={scoreColor(position.healthScore)}>
            {position.healthScore}/100
          </span>
        }
      />
      <InfoRow
        label="Risk Score"
        value={
          <span className={riskColor(position.riskScore)}>
            {position.riskScore}/100
          </span>
        }
      />
      <InfoRow
        label="Performance Score"
        value={
          <span className={scoreColor(position.performanceScore)}>
            {position.performanceScore}/100
          </span>
        }
      />
      <InfoRow
        label="Stability Score"
        value={
          <span className={scoreColor(position.stabilityScore)}>
            {position.stabilityScore}/100
          </span>
        }
      />
      <InfoRow label="Fluctuation" value={`${position.fluctuation.toFixed(1)}%`} />
    </SectionCard>
  )
}
