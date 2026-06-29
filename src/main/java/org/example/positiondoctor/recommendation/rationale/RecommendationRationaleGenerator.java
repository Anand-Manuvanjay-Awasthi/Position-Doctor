package org.example.positiondoctor.recommendation.rationale;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;
import org.example.positiondoctor.recommendation.explanation.RecommendationExplanationGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationRationaleGenerator {

    private final RecommendationExplanationGenerator explanationGenerator;

    public String generate(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        return explanationGenerator.generateCombinedRationale(primaryRecommendation, secondaryRecommendations);
    }
}
