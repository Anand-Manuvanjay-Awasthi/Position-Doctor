package org.example.positiondoctor.recommendation.mapper;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.recommendation.dto.RecommendationExplanation;
import org.example.positiondoctor.recommendation.dto.RecommendationResponse;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;
import org.example.positiondoctor.recommendation.explanation.RecommendationExplanationGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationResponseBuilder {

    private final RecommendationExplanationGenerator explanationGenerator;

    public RecommendationResponse build(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            int confidence,
            String rationale,
            FundamentalStrengthReport fundamentalStrengthReport,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        RecommendationExplanation explanation = explanationGenerator.generateExplanation(
                primaryRecommendation,
                secondaryRecommendations,
                fundamentalStrengthReport.getStrengthLevel()
        );

        return RecommendationResponse.builder()
                .primaryRecommendation(primaryRecommendation)
                .secondaryRecommendations(secondaryRecommendations)
                .confidence(confidence)
                .rationale(rationale)
                .explanation(explanation)
                .fundamentalStrength(fundamentalStrengthReport.getStrengthLevel())
                .healthScore(healthReport.getHealthScore())
                .fearGreedLevel(marketContextReport.getFearGreedLevel())
                .build();
    }
}
