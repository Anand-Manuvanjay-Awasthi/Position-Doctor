import axios from "axios"

/**
 * Pre-configured Axios instance.
 *
 * The backend is not wired up yet (dummy data is used across the app),
 * but this client is ready to point at the Spring Boot API once available.
 * Set VITE_API_BASE_URL in an .env file to override the default.
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
})

export default api
