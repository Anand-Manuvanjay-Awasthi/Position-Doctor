import { useMemo, useState } from "react"
import AlertTable from "../components/AlertTable"
import StatCard from "../components/StatCard"
import { alertRows } from "../data/dashboardData"
import type { AlertRow } from "../types"

export default function AlertsPage() {
  const [alerts, setAlerts] = useState<AlertRow[]>(alertRows)

  const unreadCount = useMemo(
    () => alerts.filter((a) => a.status === "UNREAD").length,
    [alerts],
  )

  function markAsRead(id: string) {
    setAlerts((prev) =>
      prev.map((a) => (a.id === id ? { ...a, status: "READ" } : a)),
    )
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

      <AlertTable alerts={alerts} onMarkAsRead={markAsRead} />
    </section>
  )
}
