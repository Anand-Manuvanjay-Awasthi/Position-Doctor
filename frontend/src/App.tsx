import { Route, Routes } from "react-router-dom"
import MainLayout from "./layouts/MainLayout"
import LandingPage from "./pages/LandingPage"
import PortfolioPage from "./pages/PortfolioPage"
import AddPositionPage from "./pages/AddPositionPage"
import PositionDetailsPage from "./pages/PositionDetailsPage"
import AlertsPage from "./pages/AlertsPage"
import DigestPage from "./pages/DigestPage"

export default function App() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<LandingPage />} />
        <Route path="/portfolio" element={<PortfolioPage />} />
        <Route path="/add-position" element={<AddPositionPage />} />
        <Route path="/positions/:id" element={<PositionDetailsPage />} />
        <Route path="/alerts" element={<AlertsPage />} />
        <Route path="/digest" element={<DigestPage />} />
      </Route>
    </Routes>
  )
}
