import type { PositionDetail } from "../types"
import SectionCard, { InfoRow } from "./SectionCard"

export default function PositionCard({
  position,
}: {
  position: PositionDetail
}) {
  return (
    <SectionCard title="Position Information">
      <InfoRow label="Stock Symbol" value={position.stockSymbol} />
      <InfoRow label="Quantity" value={position.quantity} />
      <InfoRow label="Buy Price" value={`$${position.buyPrice.toFixed(2)}`} />
      <InfoRow
        label="Current Price"
        value={`$${position.currentPrice.toFixed(2)}`}
      />
      <InfoRow label="Stop Loss" value={`$${position.stopLoss.toFixed(2)}`} />
      <InfoRow
        label="Target Price"
        value={`$${position.targetPrice.toFixed(2)}`}
      />
    </SectionCard>
  )
}
