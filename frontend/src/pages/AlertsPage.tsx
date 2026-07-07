import { SeverityBadge } from "../components/Badge"
import { useAlerts } from "../hooks/usePortfolio"

function formatDate(iso: string) {
  return new Date(iso).toLocaleString(undefined, {
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  })
}

export default function AlertsPage() {
  const { data: alerts, loading, error } = useAlerts()

  return (
    <section>
      <h1 className="mb-1 text-2xl font-bold text-slate-900">Alerts</h1>
      <p className="mb-6 text-sm text-slate-500">
        Notifications triggered by changes in your positions.
      </p>

      {loading && <p className="text-sm text-slate-500">Loading alerts...</p>}
      {error && <p className="text-sm text-red-600">{error}</p>}

      {alerts && (
        <ul className="space-y-3">
          {alerts.map((alert) => (
            <li
              key={alert.id}
              className="rounded-lg border border-slate-200 bg-white p-4"
            >
              <div className="flex items-center justify-between gap-3">
                <span className="font-semibold text-slate-900">
                  {alert.ticker}
                </span>
                <SeverityBadge value={alert.severity} />
              </div>
              <p className="mt-1 text-sm text-slate-700">{alert.message}</p>
              <p className="mt-2 text-xs text-slate-400">
                {formatDate(alert.createdAt)}
              </p>
            </li>
          ))}
        </ul>
      )}
    </section>
  )
}
