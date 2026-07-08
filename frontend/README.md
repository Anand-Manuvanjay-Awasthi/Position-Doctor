# Position Doctor - Frontend

React frontend for the **Position Doctor** hackathon project. It monitors
investment positions and surfaces portfolio health, recommendations, alerts,
and digest data from the Spring Boot backend.

## Tech Stack

- React 19 + TypeScript
- Vite
- Tailwind CSS
- React Router DOM
- Axios service layer

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
  services/      # axios client + backend service functions
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

## Backend Connection

Set `VITE_API_BASE_URL` in a `.env` file to point at the Spring Boot API.
For local development:

```bash
VITE_API_BASE_URL=http://localhost:8080
```
