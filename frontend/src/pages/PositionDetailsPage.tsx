import { Link, useParams } from "react-router-dom"
import PositionCard from "../components/PositionCard"
import HealthCard from "../components/HealthCard"
import MarketCard from "../components/MarketCard"
import FundamentalCard from "../components/FundamentalCard"
import RecommendationCard from "../components/RecommendationCard"
import CompanyInsightsCard from "../components/CompanyInsightsCard"
import SectionCard from "../components/SectionCard"
import { useAsync } from "../hooks/useAsync"
import { getPositionDetailById } from "../services/positionService"

export default function PositionDetailsPage() {
  const { id = "" } = useParams()
  const {
    data: position,
    loading,
    error,
  } = useAsync(() => getPositionDetailById(id), [id])

  return (
    <section>
      <div className="mb-6">
        <Link
          to="/portfolio"
          className="text-sm font-medium text-slate-500 hover:text-slate-900"
        >
          &larr; Back to Portfolio
        </Link>
      </div>

      {loading && <p className="text-sm text-slate-500">Loading position...</p>}
      {error && <p className="text-sm text-red-600">{error}</p>}
      {!loading && !error && !position && (
        <p className="text-sm text-slate-500">Position not found.</p>
      )}

      {position && (
        <>
          <div className="mb-6">
            <h1 className="text-2xl font-bold text-slate-900">
              {position.stockSymbol}
            </h1>
          </div>

          <div className="grid grid-cols-1 gap-5 lg:grid-cols-2">
            <PositionCard position={position} />
            <HealthCard position={position} />
            <MarketCard position={position} />
            <FundamentalCard position={position} />
            <RecommendationCard position={position} />
            <CompanyInsightsCard insights={position.companyInsights} />
          </div>

          <div className="mt-5">
            <SectionCard title="Recommendation Explanation">
              <div className="text-sm">
                <p className="mb-1 text-slate-500">Recommendation Rationale</p>
                <p className="leading-relaxed text-slate-700">
                  {position.rationale}
                </p>
              </div>
            </SectionCard>
          </div>
        </>
      )}
    </section>
  )
}
