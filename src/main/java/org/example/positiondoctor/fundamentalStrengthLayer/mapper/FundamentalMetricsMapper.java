package org.example.positiondoctor.fundamentalStrengthLayer.mapper;

import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalMetricsRequest;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.fundamentalStrengthLayer.entity.FundamentalMetrics;
import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class FundamentalMetricsMapper {

    public FundamentalMetrics toEntity(FundamentalMetricsRequest request) {
        return FundamentalMetrics.builder()
                .stockSymbol(normalizeStockSymbol(request.getStockSymbol()))
                .eps(request.getEps())
                .roe(request.getRoe())
                .build();
    }

    public FundamentalStrengthReport toReport(
            FundamentalMetrics metrics,
            int strengthScore,
            StrengthLevel strengthLevel,
            String explanation
    ) {
        return FundamentalStrengthReport.builder()
                .strengthScore(strengthScore)
                .strengthLevel(strengthLevel)
                .eps(metrics.getEps())
                .roe(metrics.getRoe())
                .explanation(explanation)
                .build();
    }

    private String normalizeStockSymbol(String stockSymbol) {
        return stockSymbol.trim().toUpperCase(Locale.US);
    }
}
