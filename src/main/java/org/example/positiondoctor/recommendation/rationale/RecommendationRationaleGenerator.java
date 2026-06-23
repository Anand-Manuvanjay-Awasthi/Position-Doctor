package org.example.positiondoctor.recommendation.rationale;

import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationRationaleGenerator {

    public String generate(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        List<String> rationaleParts = new ArrayList<>();

        rationaleParts.add(primaryRationale(primaryRecommendation, marketContextReport.getFearGreedLevel()));
        secondaryRecommendations.forEach(
                secondaryRecommendation -> rationaleParts.add(secondaryRationale(secondaryRecommendation))
        );

        return String.join(" ", rationaleParts);
    }

    private String primaryRationale(
            PrimaryRecommendation primaryRecommendation,
            FearGreedLevel fearGreedLevel
    ) {
        if (primaryRecommendation == PrimaryRecommendation.HOLD && fearGreedLevel == FearGreedLevel.EXTREME_FEAR) {
            return "Position health is strong, but market sentiment is extremely fearful.";
        }
        if (primaryRecommendation == PrimaryRecommendation.HOLD && fearGreedLevel == FearGreedLevel.EXTREME_GREED) {
            return "Position health is strong, but market sentiment appears overheated.";
        }

        return switch (primaryRecommendation) {
            case STRONG_HOLD -> "Position health is excellent and market sentiment is not extreme.";
            case HOLD -> "Position health supports holding the position.";
            case WATCH_CLOSELY -> "Position health is mixed and should be monitored closely.";
            case REDUCE_POSITION -> "Position health has weakened and reducing exposure may lower risk.";
            case CONSIDER_EXIT -> "Position health is poor and exiting the position should be considered.";
        };
    }

    private String secondaryRationale(SecondaryRecommendation secondaryRecommendation) {
        return switch (secondaryRecommendation) {
            case BOOK_PROFIT -> "Partial profit booking should be considered.";
            case TIGHTEN_STOP_LOSS -> "Tightening the stop-loss can help protect existing gains.";
            case HEDGE_POSITION -> "Hedging may help manage downside risk.";
        };
    }
}
