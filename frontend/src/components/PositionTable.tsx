import { Link } from "react-router-dom"
import type { PositionSummary } from "../types"
import { RecommendationBadge } from "./Badge"

interface Props {
  positions: PositionSummary[]
}

function scoreColor(score: number | null) {
  if (score === null) return "text-slate-500"
  if (score >= 75) return "text-green-700"
  if (score >= 50) return "text-amber-700"
  return "text-red-700"
}

export default function PositionTable({ positions }: Props) {
  return (
    <div className="overflow-x-auto rounded-lg border border-slate-200 bg-white">
      <table className="w-full min-w-[640px] text-left text-sm">
        <thead className="border-b border-slate-200 bg-slate-50 text-xs uppercase tracking-wide text-slate-500">
          <tr>
            <th className="px-4 py-3 font-medium">Stock Name</th>
            <th className="px-4 py-3 font-medium">Health Score</th>
            <th className="px-4 py-3 font-medium">Recommendation</th>
            <th className="px-4 py-3 font-medium">Confidence</th>
            <th className="px-4 py-3 font-medium text-right">Details</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100">
          {positions.map((position) => (
            <tr key={position.id} className="hover:bg-slate-50">
              <td className="px-4 py-3">
                <div className="font-medium text-slate-900">
                  {position.stockSymbol}
                </div>
              </td>
              <td className={`px-4 py-3 font-semibold ${scoreColor(position.healthScore)}`}>
                {position.healthScore === null ? (
                  "N/A"
                ) : (
                  <>
                    {position.healthScore}
                    <span className="text-slate-400">/100</span>
                  </>
                )}
              </td>
              <td className="px-4 py-3">
                {position.primaryRecommendation ? (
                  <RecommendationBadge value={position.primaryRecommendation} />
                ) : (
                  <span className="text-slate-500">N/A</span>
                )}
              </td>
              <td className="px-4 py-3 text-slate-700">
                {position.confidence === null ? "N/A" : `${position.confidence}%`}
              </td>
              <td className="px-4 py-3 text-right">
                <Link
                  to={`/positions/${position.id}`}
                  className="inline-block rounded-md border border-slate-300 px-3 py-1.5 text-xs font-medium text-slate-700 hover:bg-slate-100"
                >
                  View Details
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
