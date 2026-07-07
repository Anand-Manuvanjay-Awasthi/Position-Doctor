# Position Doctor - Frontend

React frontend for the **Position Doctor** hackathon project. It monitors
investment positions and surfaces intelligent recommendations. This app ships
with dummy data and is **not** wired to the backend yet.

## Tech Stack

- React 19 + TypeScript
- Vite
- Tailwind CSS
- React Router DOM
- Axios (pre-configured client, ready for backend integration)

## Getting Started

```bash
cd frontend
npm install
npm run dev
```

The app runs at `http://localhost:5173`.

## Available Scripts

- `npm run dev` - start the dev server
- `npm run build` - type-check and build for production
- `npm run preview` - preview the production build

## Project Structure

```
src/
  assets/        # static assets
  components/    # reusable UI (Navbar, tables, badges, modal)
  hooks/         # data-loading hooks (useAsync, usePortfolio)
  layouts/       # shared page layout with navbar + footer
  pages/         # route pages (Landing, Portfolio, Add, Alerts, Digest)
  services/      # axios client + dummy data service
  types/         # shared TypeScript types
```

## Routes

| Path            | Page          |
| --------------- | ------------- |
| `/`             | Landing       |
| `/portfolio`    | Portfolio     |
| `/add-position` | Add Position  |
| `/alerts`       | Alerts        |
| `/digest`       | Daily Digest  |

## Connecting the Backend

Data currently comes from `src/services/positionService.ts` as dummy data.
Each function has a commented example showing the equivalent Axios call. Set
`VITE_API_BASE_URL` in a `.env` file to point at the Spring Boot API.
