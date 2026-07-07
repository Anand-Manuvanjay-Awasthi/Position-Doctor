type Tone = "slate" | "green" | "amber" | "red"

const valueTone: Record<Tone, string> = {
  slate: "text-slate-900",
  green: "text-green-700",
  amber: "text-amber-700",
  red: "text-red-700",
}

interface Props {
  label: string
  value: string | number
  tone?: Tone
}

export default function DigestCard({ label, value, tone = "slate" }: Props) {
  return (
    <div className="rounded-lg border border-slate-200 bg-white p-4 text-center">
      <div className={`text-2xl font-bold ${valueTone[tone]}`}>{value}</div>
      <div className="mt-1 text-xs uppercase tracking-wide text-slate-500">
        {label}
      </div>
    </div>
  )
}
