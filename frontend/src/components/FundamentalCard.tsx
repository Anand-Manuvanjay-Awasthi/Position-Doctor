import type { PositionDetail } from "../types"
import SectionCard, { InfoRow } from "./SectionCard"

export default function FundamentalCard({
  position,
}: {
  position: PositionDetail
}) {
  return (
    <SectionCard title="Fundamental Analysis">
      <InfoRow label="EPS" value={position.eps.toFixed(2)} />
      <InfoRow label="ROE" value={`${position.roe.toFixed(1)}%`} />
      <InfoRow label="Fundamental Strength" value={position.fundamentalStrength} />
    </SectionCard>
  )
}
