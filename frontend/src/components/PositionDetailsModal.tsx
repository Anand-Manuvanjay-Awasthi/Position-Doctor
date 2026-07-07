import type { Position } from "../types"
import { HealthBadge, RecommendationBadge } from "./Badge"

interface Props {
  position: Position | null
  onClose: () => void
}

function Row({ label, value }: { label: string; value: React.ReactNode }) {
  return (
    <div className="flex items-center justify-between border-b border-slate-100 py-2 text-sm last:border-b-0">
      <span className="text-slate-500">{label}</span>
      <span className="font-medium text-slate-900">{value}</span>
    </div>
  )
}

export default function PositionDetailsModal({ position, onClose }: Props) {
  if (!position) return null

  const invested = position.buyPrice * position.quantity
  const current = position.currentPrice * position.quantity
  const pnl = current - invested
  const pnlPct = (pnl / invested) * 100

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/40 p-4"
      role="dialog"
      aria-modal="true"
      aria-labelledby="position-details-title"
      onClick={onClose}
    >
      <div
        className="w-full max-w-md rounded-lg bg-white p-5 shadow-lg"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="mb-4 flex items-start justify-between">
          <div>
            <h2
              id="position-details-title"
              className="text-lg font-semibold text-slate-900"
            >
              {position.stockName}
            </h2>
            <p className="text-sm text-slate-500">{position.ticker}</p>
          </div>
          <button
            type="button"
            onClick={onClose}
            className="rounded-md px-2 py-1 text-sm text-slate-500 hover:bg-slate-100"
            aria-label="Close details"
          >
            Close
          </button>
        </div>

        <div className="mb-4 flex gap-2">
          <HealthBadge value={position.healthStatus} />
          <RecommendationBadge value={position.recommendation} />
        </div>

        <Row label="Health Score" value={`${position.healthScore}/100`} />
        <Row label="Confidence" value={`${Math.round(position.confidence * 100)}%`} />
        <Row label="Quantity" value={position.quantity} />
        <Row label="Buy Price" value={`$${position.buyPrice.toFixed(2)}`} />
        <Row label="Current Price" value={`$${position.currentPrice.toFixed(2)}`} />
        <Row label="Invested" value={`$${invested.toFixed(2)}`} />
        <Row
          label="Unrealized P&L"
          value={
            <span className={pnl >= 0 ? "text-green-700" : "text-red-700"}>
              {pnl >= 0 ? "+" : ""}
              {pnl.toFixed(2)} ({pnlPct.toFixed(1)}%)
            </span>
          }
        />
      </div>
    </div>
  )
}
