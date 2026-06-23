package org.example.positiondoctor.recommendation.confidence;

import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.springframework.stereotype.Component;

@Component
public class RecommendationConfidenceCalculator {

    public int calculate(PositionHealthReport healthReport, MarketContextReport marketContextReport) {
        int confidence = healthReport.getHealthScore() + marketModifier(marketContextReport.getFearGreedLevel());
        return Math.max(0, Math.min(confidence, 100));
    }

    private int marketModifier(FearGreedLevel fearGreedLevel) {
        return switch (fearGreedLevel) {
            case EXTREME_FEAR -> -20;
            case FEAR -> -10;
            case NEUTRAL -> 0;
            case GREED -> 5;
            case EXTREME_GREED -> -5;
        };
    }
}
