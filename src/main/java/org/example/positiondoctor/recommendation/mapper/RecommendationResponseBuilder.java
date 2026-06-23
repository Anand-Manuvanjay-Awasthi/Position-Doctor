package org.example.positiondoctor.recommendation.mapper;

import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.recommendation.dto.RecommendationResponse;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationResponseBuilder {

    public RecommendationResponse build(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            int confidence,
            String rationale,
            FundamentalStrengthReport fundamentalStrengthReport,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        return RecommendationResponse.builder()
                .primaryRecommendation(primaryRecommendation)
                .secondaryRecommendations(secondaryRecommendations)
                .confidence(confidence)
                .rationale(rationale)
                .fundamentalStrength(fundamentalStrengthReport.getStrengthLevel())
                .healthScore(healthReport.getHealthScore())
                .fearGreedLevel(marketContextReport.getFearGreedLevel())
                .build();
    }
}
