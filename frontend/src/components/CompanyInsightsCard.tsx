import SectionCard from "./SectionCard"

export default function CompanyInsightsCard({ insights }: { insights: string }) {
  return (
    <SectionCard title="Company Insights">
      <p className="text-sm leading-relaxed text-slate-700">{insights}</p>
    </SectionCard>
  )
}
