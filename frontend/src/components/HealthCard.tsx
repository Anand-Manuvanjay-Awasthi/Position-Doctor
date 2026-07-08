import type { PositionDetail } from "../types"
import { HealthBadge } from "./Badge"
import SectionCard, { InfoRow } from "./SectionCard"

function scoreColor(score: number, maxScore = 100) {
  const percentage = (score / maxScore) * 100

  if (percentage >= 75) return "text-green-700"
  if (percentage >= 50) return "text-amber-700"
  return "text-red-700"
}

function riskColor(score: number) {
  if (score >= 30) return "text-green-700"
  if (score >= 20) return "text-amber-700"
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
      <InfoRow label="Health Status" value={<HealthBadge value={position.healthStatus} />} />
      <InfoRow
        label="Risk Score"
        value={
          <span className={riskColor(position.riskScore)}>
            {position.riskScore}/40
          </span>
        }
      />
      <InfoRow label="Risk Level" value={position.riskLevel} />
      <InfoRow
        label="Performance Score"
        value={
          <span className={scoreColor(position.performanceScore, 40)}>
            {position.performanceScore}/40
          </span>
        }
      />
      <InfoRow
        label="Stability Score"
        value={
          <span className={scoreColor(position.stabilityScore, 20)}>
            {position.stabilityScore}/20
          </span>
        }
      />
      <InfoRow label="Fluctuation Level" value={position.fluctuationLevel} />
    </SectionCard>
  )
}
