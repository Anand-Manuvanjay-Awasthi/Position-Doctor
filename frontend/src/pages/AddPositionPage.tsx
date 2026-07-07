import { useState } from "react"
import { createPosition } from "../services/positionService"
import type { NewPosition } from "../types"

const emptyForm: NewPosition = {
  stockName: "",
  ticker: "",
  quantity: 0,
  buyPrice: 0,
  targetPrice: 0,
  stopLoss: 0,
}

const fields: {
  name: keyof NewPosition
  label: string
  type: "text" | "number"
}[] = [
  { name: "stockName", label: "Stock Name", type: "text" },
  { name: "ticker", label: "Ticker", type: "text" },
  { name: "quantity", label: "Quantity", type: "number" },
  { name: "buyPrice", label: "Buy Price", type: "number" },
  { name: "targetPrice", label: "Target Price", type: "number" },
  { name: "stopLoss", label: "Stop Loss", type: "number" },
]

export default function AddPositionPage() {
  const [form, setForm] = useState<NewPosition>(emptyForm)
  const [submitted, setSubmitted] = useState(false)

  function handleChange(name: keyof NewPosition, value: string, isNumber: boolean) {
    setForm((prev) => ({
      ...prev,
      [name]: isNumber ? Number(value) : value,
    }))
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    await createPosition(form)
    setSubmitted(true)
  }

  function handleReset() {
    setForm(emptyForm)
    setSubmitted(false)
  }

  return (
    <section className="max-w-xl">
      <h1 className="mb-1 text-2xl font-bold text-slate-900">Add Position</h1>
      <p className="mb-6 text-sm text-slate-500">
        Enter a new position to track. This demo does not persist data.
      </p>

      {submitted ? (
        <div className="rounded-lg border border-green-200 bg-green-50 p-4">
          <p className="text-sm font-medium text-green-800">
            Position for {form.ticker || form.stockName} was captured.
          </p>
          <button
            type="button"
            onClick={handleReset}
            className="mt-3 rounded-md border border-green-300 px-3 py-1.5 text-sm font-medium text-green-800 hover:bg-green-100"
          >
            Add another
          </button>
        </div>
      ) : (
        <form
          onSubmit={handleSubmit}
          className="rounded-lg border border-slate-200 bg-white p-5"
        >
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
            {fields.map((field) => (
              <label key={field.name} className="block text-sm">
                <span className="mb-1 block font-medium text-slate-700">
                  {field.label}
                </span>
                <input
                  type={field.type}
                  required
                  min={field.type === "number" ? 0 : undefined}
                  step={field.type === "number" ? "any" : undefined}
                  value={form[field.name] === 0 ? "" : form[field.name]}
                  onChange={(e) =>
                    handleChange(field.name, e.target.value, field.type === "number")
                  }
                  className="w-full rounded-md border border-slate-300 px-3 py-2 text-slate-900 outline-none focus:border-slate-500 focus:ring-1 focus:ring-slate-500"
                />
              </label>
            ))}
          </div>

          <div className="mt-6 flex gap-3">
            <button
              type="submit"
              className="rounded-md bg-slate-900 px-4 py-2 text-sm font-medium text-white hover:bg-slate-800"
            >
              Save Position
            </button>
            <button
              type="button"
              onClick={handleReset}
              className="rounded-md border border-slate-300 px-4 py-2 text-sm font-medium text-slate-700 hover:bg-slate-100"
            >
              Reset
            </button>
          </div>
        </form>
      )}
    </section>
  )
}
