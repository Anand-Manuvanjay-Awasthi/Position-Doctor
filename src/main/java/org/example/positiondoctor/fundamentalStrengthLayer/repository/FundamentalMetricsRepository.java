package org.example.positiondoctor.fundamentalStrengthLayer.repository;

import org.example.positiondoctor.fundamentalStrengthLayer.entity.FundamentalMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundamentalMetricsRepository extends JpaRepository<FundamentalMetrics, Long> {

    Optional<FundamentalMetrics> findTopByStockSymbolIgnoreCaseOrderByCreatedAtDesc(String stockSymbol);
}
