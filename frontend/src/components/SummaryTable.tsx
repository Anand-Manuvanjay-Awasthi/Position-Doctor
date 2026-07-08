import type { DigestSummaryRow } from "../types"
import { RecommendationBadge } from "./Badge"

interface Props {
  rows: DigestSummaryRow[]
}

function scoreColor(score: number) {
  if (score >= 75) return "text-green-700"
  if (score >= 50) return "text-amber-700"
  return "text-red-700"
}

export default function SummaryTable({ rows }: Props) {
  return (
    <div className="overflow-x-auto rounded-lg border border-slate-200 bg-white">
      <table className="w-full min-w-[640px] text-left text-sm">
        <thead className="border-b border-slate-200 bg-slate-50 text-xs uppercase tracking-wide text-slate-500">
          <tr>
            <th className="px-4 py-3 font-medium">Stock</th>
            <th className="px-4 py-3 font-medium">Health Score</th>
            <th className="px-4 py-3 font-medium">Recommendation</th>
            <th className="px-4 py-3 font-medium">Confidence</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100">
          {rows.map((row) => (
            <tr key={row.ticker} className="hover:bg-slate-50">
              <td className="px-4 py-3">
                <div className="font-medium text-slate-900">{row.stock}</div>
                <div className="text-xs text-slate-500">{row.ticker}</div>
              </td>
              <td className={`px-4 py-3 font-semibold ${scoreColor(row.healthScore)}`}>
                {row.healthScore}
                <span className="text-slate-400">/100</span>
              </td>
              <td className="px-4 py-3">
                <RecommendationBadge value={row.recommendation} />
              </td>
              <td className="px-4 py-3 text-slate-700">
                {Math.round(row.confidence * 100)}%
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
