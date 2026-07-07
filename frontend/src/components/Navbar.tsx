import { NavLink } from "react-router-dom"

const links = [
  { to: "/portfolio", label: "Portfolio" },
  { to: "/add-position", label: "Add Position" },
  { to: "/alerts", label: "Alerts" },
  { to: "/digest", label: "Daily Digest" },
]

export default function Navbar() {
  return (
    <header className="border-b border-slate-200 bg-white">
      <nav className="mx-auto flex max-w-5xl items-center justify-between gap-4 px-4 py-3">
        <NavLink to="/" className="text-lg font-semibold text-slate-900">
          Position Doctor
        </NavLink>
        <ul className="flex flex-wrap items-center gap-1">
          {links.map((link) => (
            <li key={link.to}>
              <NavLink
                to={link.to}
                className={({ isActive }) =>
                  `rounded-md px-3 py-1.5 text-sm font-medium transition-colors ${
                    isActive
                      ? "bg-slate-900 text-white"
                      : "text-slate-600 hover:bg-slate-100"
                  }`
                }
              >
                {link.label}
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>
    </header>
  )
}
