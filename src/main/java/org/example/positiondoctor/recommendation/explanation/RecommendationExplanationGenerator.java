package org.example.positiondoctor.recommendation.explanation;

import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;
import org.example.positiondoctor.recommendation.dto.RecommendationExplanation;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RecommendationExplanationGenerator {

    public String generatePrimaryRationale(PrimaryRecommendation primaryRecommendation) {
        Objects.requireNonNull(primaryRecommendation, "primary recommendation must not be null");

        return switch (primaryRecommendation) {
            case STRONG_HOLD -> "The position appears healthy and does not require immediate action.";
            case HOLD -> "The position can be held, but conditions should continue to be monitored.";
            case WATCH_CLOSELY -> "The position is showing mixed signals and deserves closer attention.";
            case REDUCE_POSITION -> "The position has weakened enough that reducing exposure may be prudent.";
            case CONSIDER_EXIT -> "The position shows significant weakness and an exit should be considered.";
        };
    }

    public String generateSecondaryRationale(SecondaryRecommendation secondaryRecommendation) {
        Objects.requireNonNull(secondaryRecommendation, "secondary recommendation must not be null");

        return switch (secondaryRecommendation) {
            case BOOK_PROFIT -> "The target is close enough that partial profit booking may be sensible.";
            case TIGHTEN_STOP_LOSS -> "The position is profitable, so tightening the stop-loss can help protect gains.";
            case HEDGE_POSITION -> "Market conditions are defensive enough that hedging may reduce downside risk.";
        };
    }

    public String generateCombinedRationale(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations
    ) {
        List<String> rationaleParts = new ArrayList<>();
        rationaleParts.add(generatePrimaryRationale(primaryRecommendation));

        if (secondaryRecommendations != null) {
            secondaryRecommendations.stream()
                    .filter(Objects::nonNull)
                    .map(this::generateSecondaryRationale)
                    .forEach(rationaleParts::add);
        }

        return String.join(" ", rationaleParts);
    }

    public String generateCompanyInsights(StrengthLevel strengthLevel) {
        Objects.requireNonNull(strengthLevel, "strength level must not be null");

        return switch (strengthLevel) {
            case STRONG -> "The company demonstrates strong profitability and efficient capital utilization.";
            case MODERATE -> "The company shows acceptable fundamentals, but profitability or capital efficiency could improve.";
            case WEAK -> "The company shows weak fundamentals based on earnings and return on equity.";
        };
    }

    public RecommendationExplanation generateExplanation(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            StrengthLevel strengthLevel
    ) {
        return RecommendationExplanation.builder()
                .recommendationRationale(generateCombinedRationale(primaryRecommendation, secondaryRecommendations))
                .companyInsights(generateCompanyInsights(strengthLevel))
                .build();
    }
}
