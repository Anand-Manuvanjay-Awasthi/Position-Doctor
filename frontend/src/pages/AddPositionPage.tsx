import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { createPosition } from "../services/positionService"
import type { NewPosition, Trend } from "../types"

const emptyForm: NewPosition = {
  stockName: "",
  quantity: 0,
  buyPrice: 0,
  currentPrice: 0,
  stopLoss: 0,
  targetPrice: 0,
  trend: "SIDEWAYS",
  fearGreedIndex: 50,
  eps: 0,
  roe: 0,
}

const numberFields: {
  name: keyof NewPosition
  label: string
  step?: string
}[] = [
  { name: "quantity", label: "Quantity", step: "1" },
  { name: "buyPrice", label: "Buy Price", step: "any" },
  { name: "currentPrice", label: "Current Price", step: "any" },
  { name: "stopLoss", label: "Stop Loss", step: "any" },
  { name: "targetPrice", label: "Target Price", step: "any" },
]

const trendOptions: Trend[] = [
  "STRONG_UPTREND",
  "UPTREND",
  "SIDEWAYS",
  "DOWNTREND",
  "STRONG_DOWNTREND",
]

type Errors = Partial<Record<keyof NewPosition, string>>

function validate(form: NewPosition): Errors {
  const errors: Errors = {}

  if (!form.stockName.trim()) {
    errors.stockName = "Stock name is required."
  }
  if (form.quantity <= 0) {
    errors.quantity = "Quantity must be greater than 0."
  }
  if (form.buyPrice <= 0) {
    errors.buyPrice = "Buy price must be greater than 0."
  }
  if (form.currentPrice <= 0) {
    errors.currentPrice = "Current price must be greater than 0."
  }
  if (form.stopLoss <= 0) {
    errors.stopLoss = "Stop loss must be greater than 0."
  }
  if (form.targetPrice <= 0) {
    errors.targetPrice = "Target price must be greater than 0."
  }
  if (form.fearGreedIndex < 0 || form.fearGreedIndex > 100) {
    errors.fearGreedIndex = "Fear & Greed Index must be between 0 and 100."
  }
  return errors
}

const inputClass =
  "w-full rounded-md border border-slate-300 px-3 py-2 text-slate-900 outline-none focus:border-slate-500 focus:ring-1 focus:ring-slate-500"

export default function AddPositionPage() {
  const navigate = useNavigate()
  const [form, setForm] = useState<NewPosition>(emptyForm)
  const [errors, setErrors] = useState<Errors>({})
  const [submitted, setSubmitted] = useState(false)
  const [saving, setSaving] = useState(false)
  const [submitError, setSubmitError] = useState<string | null>(null)

  function update<K extends keyof NewPosition>(name: K, value: NewPosition[K]) {
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  function handleNumber(name: keyof NewPosition, raw: string) {
    update(name, (raw === "" ? 0 : Number(raw)) as never)
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    const nextErrors = validate(form)
    setErrors(nextErrors)
    if (Object.keys(nextErrors).length > 0) return

    setSubmitError(null)
    setSaving(true)
    try {
      await createPosition(form)
      setSubmitted(true)
    } catch {
      setSubmitError(
        "Unable to reach the backend. Make sure the API is running and reachable at the configured VITE_API_BASE_URL.",
      )
    } finally {
      setSaving(false)
    }
  }

  function handleReset() {
    setForm(emptyForm)
    setErrors({})
    setSubmitted(false)
    setSubmitError(null)
  }

  if (submitted) {
    return (
      <section className="max-w-2xl">
        <div className="rounded-lg border border-green-200 bg-green-50 p-5">
          <p className="text-sm font-medium text-green-800">
            Position for {form.stockName} was captured.
          </p>
          <div className="mt-4 flex gap-3">
            <button
              type="button"
              onClick={handleReset}
              className="rounded-md border border-green-300 px-3 py-1.5 text-sm font-medium text-green-800 hover:bg-green-100"
            >
              Add another
            </button>
            <button
              type="button"
              onClick={() => navigate("/portfolio")}
              className="rounded-md bg-slate-900 px-3 py-1.5 text-sm font-medium text-white hover:bg-slate-800"
            >
              Go to Portfolio
            </button>
          </div>
        </div>
      </section>
    )
  }

  return (
    <section className="max-w-2xl">
      <h1 className="mb-1 text-2xl font-bold text-slate-900">Add Position</h1>
      <p className="mb-6 text-sm text-slate-500">
        Enter a new position to track. This demo does not persist data.
      </p>

      <form
        onSubmit={handleSubmit}
        noValidate
        className="rounded-lg border border-slate-200 bg-white p-5"
      >
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
          <label className="block text-sm sm:col-span-2">
            <span className="mb-1 block font-medium text-slate-700">
              Stock Name
            </span>
            <input
              type="text"
              value={form.stockName}
              onChange={(e) => update("stockName", e.target.value)}
              className={inputClass}
            />
            {errors.stockName && (
              <span className="mt-1 block text-xs text-red-600">
                {errors.stockName}
              </span>
            )}
          </label>

          {numberFields.map((field) => (
            <label key={field.name} className="block text-sm">
              <span className="mb-1 block font-medium text-slate-700">
                {field.label}
              </span>
              <input
                type="number"
                step={field.step}
                value={form[field.name] === 0 ? "" : (form[field.name] as number)}
                onChange={(e) => handleNumber(field.name, e.target.value)}
                className={inputClass}
              />
              {errors[field.name] && (
                <span className="mt-1 block text-xs text-red-600">
                  {errors[field.name]}
                </span>
              )}
            </label>
          ))}

          <label className="block text-sm">
            <span className="mb-1 block font-medium text-slate-700">Trend</span>
            <select
              value={form.trend}
              onChange={(e) => update("trend", e.target.value as Trend)}
              className={inputClass}
            >
              {trendOptions.map((option) => (
                <option key={option} value={option}>
                  {option.replace(/_/g, " ")}
                </option>
              ))}
            </select>
          </label>

          <label className="block text-sm">
            <span className="mb-1 block font-medium text-slate-700">
              Fear &amp; Greed Index
            </span>
            <input
              type="number"
              min={0}
              max={100}
              step="1"
              value={form.fearGreedIndex}
              onChange={(e) => handleNumber("fearGreedIndex", e.target.value)}
              className={inputClass}
            />
            {errors.fearGreedIndex && (
              <span className="mt-1 block text-xs text-red-600">
                {errors.fearGreedIndex}
              </span>
            )}
          </label>

          <label className="block text-sm">
            <span className="mb-1 block font-medium text-slate-700">EPS</span>
            <input
              type="number"
              step="any"
              value={form.eps === 0 ? "" : form.eps}
              onChange={(e) => handleNumber("eps", e.target.value)}
              className={inputClass}
            />
          </label>

          <label className="block text-sm">
            <span className="mb-1 block font-medium text-slate-700">ROE</span>
            <input
              type="number"
              step="any"
              value={form.roe === 0 ? "" : form.roe}
              onChange={(e) => handleNumber("roe", e.target.value)}
              className={inputClass}
            />
          </label>
        </div>

        {submitError && (
          <div className="mt-6 rounded-md border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700">
            {submitError}
          </div>
        )}

        <div className="mt-6 flex gap-3">
          <button
            type="submit"
            disabled={saving}
            className="rounded-md bg-slate-900 px-4 py-2 text-sm font-medium text-white hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-60"
          >
            {saving ? "Saving..." : "Save Position"}
          </button>
          <button
            type="button"
            onClick={() => navigate("/portfolio")}
            className="rounded-md border border-slate-300 px-4 py-2 text-sm font-medium text-slate-700 hover:bg-slate-100"
          >
            Cancel
          </button>
        </div>
      </form>
    </section>
  )
}
