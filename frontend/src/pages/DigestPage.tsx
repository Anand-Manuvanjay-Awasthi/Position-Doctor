import { RecommendationBadge } from "../components/Badge"
import { useDigest } from "../hooks/usePortfolio"

function Stat({ label, value }: { label: string; value: number }) {
  return (
    <div className="rounded-lg border border-slate-200 bg-white p-4 text-center">
      <div className="text-2xl font-bold text-slate-900">{value}</div>
      <div className="text-xs uppercase tracking-wide text-slate-500">
        {label}
      </div>
    </div>
  )
}

export default function DigestPage() {
  const { data: digest, loading, error } = useDigest()

  return (
    <section>
      <h1 className="mb-1 text-2xl font-bold text-slate-900">Daily Digest</h1>
      <p className="mb-6 text-sm text-slate-500">
        A summary of your portfolio and today&apos;s key recommendations.
      </p>

      {loading && <p className="text-sm text-slate-500">Loading digest...</p>}
      {error && <p className="text-sm text-red-600">{error}</p>}

      {digest && (
        <div className="space-y-6">
          <div className="rounded-lg border border-slate-200 bg-white p-5">
            <p className="text-xs uppercase tracking-wide text-slate-400">
              {digest.date}
            </p>
            <p className="mt-2 text-slate-700">{digest.overview}</p>
          </div>

          <div className="grid grid-cols-3 gap-3">
            <Stat label="Positions" value={digest.totalPositions} />
            <Stat label="Healthy" value={digest.healthyCount} />
            <Stat label="At Risk" value={digest.atRiskCount} />
          </div>

          <div>
            <h2 className="mb-3 text-lg font-semibold text-slate-900">
              Highlights
            </h2>
            <ul className="space-y-3">
              {digest.items.map((item) => (
                <li
                  key={item.ticker}
                  className="rounded-lg border border-slate-200 bg-white p-4"
                >
                  <div className="flex items-center justify-between gap-3">
                    <div>
                      <span className="font-semibold text-slate-900">
                        {item.stockName}
                      </span>
                      <span className="ml-2 text-xs text-slate-500">
                        {item.ticker}
                      </span>
                    </div>
                    <RecommendationBadge value={item.recommendation} />
                  </div>
                  <p className="mt-1 text-sm text-slate-700">{item.summary}</p>
                </li>
              ))}
            </ul>
          </div>
        </div>
      )}
    </section>
  )
}
