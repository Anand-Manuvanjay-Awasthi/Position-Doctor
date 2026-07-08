import axios from "axios"

/**
 * Pre-configured Axios instance shared by the service layer.
 *
 * The base URL is driven entirely by the `VITE_API_BASE_URL` environment
 * variable (see `.env.example`). URLs are never hardcoded in the app.
 * Individual services append versioned paths such as `/api/v1/positions`.
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
})

export default api
