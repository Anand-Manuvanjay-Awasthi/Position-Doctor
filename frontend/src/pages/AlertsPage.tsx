import { useEffect, useMemo, useState } from "react"
import AlertTable from "../components/AlertTable"
import StatCard from "../components/StatCard"
import { getAlerts, markAlertAsRead } from "../services/alertService"
import type { AlertRow } from "../types"

export default function AlertsPage() {
  const [alerts, setAlerts] = useState<AlertRow[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let active = true
    setLoading(true)
    setError(null)

    getAlerts()
      .then((data) => {
        if (active) setAlerts(data)
      })
      .catch(() => {
        if (active) setError("Unable to load alerts. Please try again later.")
      })
      .finally(() => {
        if (active) setLoading(false)
      })

    return () => {
      active = false
    }
  }, [])

  const unreadCount = useMemo(
    () => alerts.filter((a) => !a.isRead).length,
    [alerts],
  )

  async function markAsRead(id: number) {
    // Optimistically update the UI, then persist to the backend.
    setAlerts((prev) =>
      prev.map((a) => (a.id === id ? { ...a, isRead: true } : a)),
    )
    try {
      await markAlertAsRead(id)
    } catch {
      // Revert on failure so the UI stays in sync with the server.
      setAlerts((prev) =>
        prev.map((a) => (a.id === id ? { ...a, isRead: false } : a)),
      )
    }
  }

  return (
    <section>
      <h1 className="mb-1 text-2xl font-bold text-slate-900">Alerts</h1>
      <p className="mb-6 text-sm text-slate-500">
        Notifications triggered by changes in your positions.
      </p>

      <div className="mb-6 grid grid-cols-2 gap-3 sm:max-w-md">
        <StatCard label="Total Alerts" value={alerts.length} />
        <StatCard label="Unread Alerts" value={unreadCount} />
      </div>

      {loading && <p className="text-sm text-slate-500">Loading alerts...</p>}
      {error && <p className="text-sm text-red-600">{error}</p>}
      {!loading && !error && alerts.length === 0 && (
        <p className="text-sm text-slate-500">No alerts to show right now.</p>
      )}

      {!loading && !error && alerts.length > 0 && (
        <AlertTable alerts={alerts} onMarkAsRead={markAsRead} />
      )}
    </section>
  )
}
