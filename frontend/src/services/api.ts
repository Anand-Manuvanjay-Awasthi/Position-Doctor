import axios from "axios"

/**
 * Pre-configured Axios instance shared by the service layer.
 *
 * The base URL is driven by the `VITE_API_BASE_URL` environment variable.
 * When it is not provided, requests use the current origin.
 * Individual services append versioned paths such as `/api/v1/positions`.
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? "",
  headers: {
    "Content-Type": "application/json",
  },
})

export default api
