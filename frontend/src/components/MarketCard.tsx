import type { PositionDetail } from "../types"
import SectionCard, { InfoRow } from "./SectionCard"

export default function MarketCard({
  position,
}: {
  position: PositionDetail
}) {
  return (
    <SectionCard title="Market Analysis">
      <InfoRow label="Fear & Greed Index" value={`${position.fearGreedIndex}/100`} />
      <div className="pt-3 text-sm">
        <p className="mb-1 text-slate-500">Market Context</p>
        <p className="leading-relaxed text-slate-700">{position.marketContext}</p>
      </div>
    </SectionCard>
  )
}
