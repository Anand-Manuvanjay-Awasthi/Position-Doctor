import { Link } from "react-router-dom"
import PositionTable from "../components/PositionTable"
import { usePositions } from "../hooks/usePortfolio"

export default function PortfolioPage() {
  const { data: positions, loading, error } = usePositions()

  return (
    <section>
      <div className="mb-6 flex items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-slate-900">Portfolio</h1>
          <p className="text-sm text-slate-500">
            Health scores and recommendations across your positions.
          </p>
        </div>
        <Link
          to="/add-position"
          className="rounded-md bg-slate-900 px-4 py-2 text-sm font-medium text-white hover:bg-slate-800"
        >
          Add Position
        </Link>
      </div>

      {loading && <p className="text-sm text-slate-500">Loading positions...</p>}
      {error && <p className="text-sm text-red-600">{error}</p>}

      {positions && <PositionTable positions={positions} />}
    </section>
  )
}
