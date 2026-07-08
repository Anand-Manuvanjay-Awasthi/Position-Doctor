import type { PositionDetail } from "../types"
import { RecommendationBadge } from "./Badge"
import SectionCard, { InfoRow } from "./SectionCard"

export default function RecommendationCard({
  position,
}: {
  position: PositionDetail
}) {
  return (
    <SectionCard title="Recommendation">
      <InfoRow
        label="Primary Recommendation"
        value={<RecommendationBadge value={position.primaryRecommendation} />}
      />
      <div className="flex items-center justify-between border-b border-slate-100 py-2 text-sm">
        <span className="text-slate-500">Secondary Recommendation(s)</span>
        <div className="flex flex-wrap justify-end gap-1.5">
          {position.secondaryRecommendations.length > 0 ? (
            position.secondaryRecommendations.map((rec) => (
              <RecommendationBadge key={rec} value={rec} />
            ))
          ) : (
            <span className="font-medium text-slate-900">None</span>
          )}
        </div>
      </div>
      <InfoRow
        label="Confidence"
        value={`${position.confidence}%`}
      />
    </SectionCard>
  )
}
