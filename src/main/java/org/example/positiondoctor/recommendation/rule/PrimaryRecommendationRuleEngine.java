package org.example.positiondoctor.recommendation.rule;

import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.springframework.stereotype.Component;

@Component
public class PrimaryRecommendationRuleEngine {

    public PrimaryRecommendation evaluate(PositionHealthReport healthReport, MarketContextReport marketContextReport) {
        int healthScore = healthReport.getHealthScore();
        FearGreedLevel fearGreedLevel = marketContextReport.getFearGreedLevel();

        if (healthScore >= 85 && blocksStrongHold(fearGreedLevel)) {
            return PrimaryRecommendation.HOLD;
        }
        if (healthScore >= 85) {
            return PrimaryRecommendation.STRONG_HOLD;
        }
        if (healthScore >= 65) {
            return PrimaryRecommendation.HOLD;
        }
        if (healthScore >= 50) {
            return PrimaryRecommendation.WATCH_CLOSELY;
        }
        if (healthScore >= 40) {
            return PrimaryRecommendation.REDUCE_POSITION;
        }

        return PrimaryRecommendation.CONSIDER_EXIT;
    }

    private boolean blocksStrongHold(FearGreedLevel fearGreedLevel) {
        return fearGreedLevel == FearGreedLevel.FEAR
                || fearGreedLevel == FearGreedLevel.EXTREME_FEAR
                || fearGreedLevel == FearGreedLevel.EXTREME_GREED;
    }
}
