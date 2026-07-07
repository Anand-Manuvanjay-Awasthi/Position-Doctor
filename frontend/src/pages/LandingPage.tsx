import { Link } from "react-router-dom"

export default function LandingPage() {
  return (
    <section className="flex flex-col items-center py-16 text-center">
      <h1 className="text-balance text-4xl font-bold tracking-tight text-slate-900 sm:text-5xl">
        Position Doctor
      </h1>
      <p className="mt-4 max-w-xl text-pretty text-lg leading-relaxed text-slate-600">
        Monitor your investment positions and receive intelligent
        recommendations.
      </p>
      <div className="mt-8 flex flex-wrap items-center justify-center gap-3">
        <Link
          to="/portfolio"
          className="rounded-md bg-slate-900 px-5 py-2.5 text-sm font-medium text-white hover:bg-slate-800"
        >
          View Portfolio
        </Link>
        <Link
          to="/add-position"
          className="rounded-md border border-slate-300 px-5 py-2.5 text-sm font-medium text-slate-700 hover:bg-slate-100"
        >
          Add Position
        </Link>
      </div>
    </section>
  )
}
