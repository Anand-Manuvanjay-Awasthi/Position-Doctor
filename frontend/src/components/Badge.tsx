import type { AlertSeverity, HealthStatus, Recommendation } from "../types"

type Tone = "green" | "blue" | "amber" | "red" | "slate"

const toneClasses: Record<Tone, string> = {
  green: "bg-green-100 text-green-800",
  blue: "bg-blue-100 text-blue-800",
  amber: "bg-amber-100 text-amber-800",
  red: "bg-red-100 text-red-800",
  slate: "bg-slate-100 text-slate-700",
}

function Badge({ tone, label }: { tone: Tone; label: string }) {
  return (
    <span
      className={`inline-block rounded-full px-2.5 py-0.5 text-xs font-medium ${toneClasses[tone]}`}
    >
      {label}
    </span>
  )
}

const recommendationTone: Record<Recommendation, Tone> = {
  BUY_MORE: "green",
  HOLD: "blue",
  WATCH: "slate",
  REDUCE: "amber",
  EXIT: "red",
}

const healthTone: Record<HealthStatus, Tone> = {
  HEALTHY: "green",
  STABLE: "blue",
  AT_RISK: "amber",
  CRITICAL: "red",
}

const severityTone: Record<AlertSeverity, Tone> = {
  INFO: "blue",
  WARNING: "amber",
  CRITICAL: "red",
}

export function RecommendationBadge({ value }: { value: Recommendation }) {
  return <Badge tone={recommendationTone[value]} label={value.replace("_", " ")} />
}

export function HealthBadge({ value }: { value: HealthStatus }) {
  return <Badge tone={healthTone[value]} label={value.replace("_", " ")} />
}

export function SeverityBadge({ value }: { value: AlertSeverity }) {
  return <Badge tone={severityTone[value]} label={value} />
}
