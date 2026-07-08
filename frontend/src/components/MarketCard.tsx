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
      <InfoRow label="Fear & Greed Level" value={position.fearGreedLevel} />
    </SectionCard>
  )
}
