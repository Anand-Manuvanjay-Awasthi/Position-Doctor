import type { AlertRow } from "../types"
import { RecommendationBadge } from "./Badge"

interface Props {
  alerts: AlertRow[]
  onMarkAsRead: (id: number) => void
}

function formatTimestamp(iso: string) {
  return new Date(iso).toLocaleString(undefined, {
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  })
}

export default function AlertTable({ alerts, onMarkAsRead }: Props) {
  return (
    <div className="overflow-x-auto rounded-lg border border-slate-200 bg-white">
      <table className="w-full min-w-[880px] text-left text-sm">
        <thead className="border-b border-slate-200 bg-slate-50 text-xs uppercase tracking-wide text-slate-500">
          <tr>
            <th className="px-4 py-3 font-medium">Stock Symbol</th>
            <th className="px-4 py-3 font-medium">Previous</th>
            <th className="px-4 py-3 font-medium">Current</th>
            <th className="px-4 py-3 font-medium">Alert Message</th>
            <th className="px-4 py-3 font-medium">Timestamp</th>
            <th className="px-4 py-3 font-medium">Status</th>
            <th className="px-4 py-3 font-medium text-right">Action</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100">
          {alerts.map((alert) => (
            <tr key={alert.id} className="hover:bg-slate-50">
              <td className="px-4 py-3 font-medium text-slate-900">
                {alert.stockSymbol}
              </td>
              <td className="px-4 py-3">
                <RecommendationBadge value={alert.previousRecommendation} />
              </td>
              <td className="px-4 py-3">
                <RecommendationBadge value={alert.currentRecommendation} />
              </td>
              <td className="px-4 py-3 text-slate-700">{alert.message}</td>
              <td className="px-4 py-3 whitespace-nowrap text-slate-500">
                {formatTimestamp(alert.createdAt)}
              </td>
              <td className="px-4 py-3">
                <span
                  className={`inline-block rounded-full px-2.5 py-0.5 text-xs font-medium ${
                    !alert.isRead
                      ? "bg-blue-100 text-blue-800"
                      : "bg-slate-100 text-slate-600"
                  }`}
                >
                  {alert.isRead ? "Read" : "Unread"}
                </span>
              </td>
              <td className="px-4 py-3 text-right">
                <button
                  type="button"
                  disabled={alert.isRead}
                  onClick={() => onMarkAsRead(alert.id)}
                  className="inline-block rounded-md border border-slate-300 px-3 py-1.5 text-xs font-medium text-slate-700 hover:bg-slate-100 disabled:cursor-not-allowed disabled:opacity-50"
                >
                  Mark as Read
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
