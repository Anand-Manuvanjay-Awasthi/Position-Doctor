import { useMemo } from "react"
import DigestCard from "../components/DigestCard"
import SummaryTable from "../components/SummaryTable"
import { digestSummaryRows } from "../data/dashboardData"

export default function DigestPage() {
  const rows = digestSummaryRows

  const stats = useMemo(() => {
    const scores = rows.map((r) => r.healthScore)
    const total = rows.length
    const average = total
      ? Math.round(scores.reduce((sum, s) => sum + s, 0) / total)
      : 0
    return {
      total,
      healthy: rows.filter((r) => r.healthScore >= 75).length,
      watch: rows.filter((r) => r.recommendation === "WATCH").length,
      reduce: rows.filter((r) => r.recommendation === "REDUCE").length,
      exit: rows.filter((r) => r.recommendation === "EXIT").length,
      average,
      highest: total ? Math.max(...scores) : 0,
      lowest: total ? Math.min(...scores) : 0,
    }
  }, [rows])

  return (
    <section>
      <h1 className="mb-1 text-2xl font-bold text-slate-900">
        Daily Portfolio Digest
      </h1>
      <p className="mb-6 text-sm text-slate-500">
        A summary of your portfolio health and today&apos;s key recommendations.
      </p>

      <div className="mb-8 grid grid-cols-2 gap-3 sm:grid-cols-4">
        <DigestCard label="Total Positions" value={stats.total} />
        <DigestCard label="Healthy Positions" value={stats.healthy} tone="green" />
        <DigestCard label="Watch Closely" value={stats.watch} tone="slate" />
        <DigestCard label="Reduce Position" value={stats.reduce} tone="amber" />
        <DigestCard label="Consider Exit" value={stats.exit} tone="red" />
        <DigestCard label="Average Health Score" value={stats.average} />
        <DigestCard label="Highest Health Score" value={stats.highest} tone="green" />
        <DigestCard label="Lowest Health Score" value={stats.lowest} tone="red" />
      </div>

      <h2 className="mb-3 text-lg font-semibold text-slate-900">
        Portfolio Summary
      </h2>
      <SummaryTable rows={rows} />
    </section>
  )
}
