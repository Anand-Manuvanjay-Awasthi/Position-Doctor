package org.example.positiondoctor.recommendation.rule;

import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Component
public class SecondaryRecommendationRuleEngine {

    private static final int CALCULATION_SCALE = 6;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal TARGET_DISTANCE_THRESHOLD = BigDecimal.valueOf(5);
    private static final BigDecimal PROFIT_THRESHOLD = BigDecimal.valueOf(15);

    public List<SecondaryRecommendation> evaluate(
            Position position,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        Set<SecondaryRecommendation> recommendations = EnumSet.noneOf(SecondaryRecommendation.class);
        FearGreedLevel fearGreedLevel = marketContextReport.getFearGreedLevel();

        applySpecialMarketOverrideActions(recommendations, healthReport, fearGreedLevel);
        applyTargetRule(recommendations, position);
        applyStopLossRule(recommendations, position, fearGreedLevel);
        applyHedgeRule(recommendations, healthReport, fearGreedLevel);

        return recommendations.stream().toList();
    }

    private void applySpecialMarketOverrideActions(
            Set<SecondaryRecommendation> recommendations,
            PositionHealthReport healthReport,
            FearGreedLevel fearGreedLevel
    ) {
        if (healthReport.getHealthScore() < 85) {
            return;
        }
        if (fearGreedLevel == FearGreedLevel.EXTREME_FEAR) {
            recommendations.add(SecondaryRecommendation.HEDGE_POSITION);
        }
        if (fearGreedLevel == FearGreedLevel.EXTREME_GREED) {
            recommendations.add(SecondaryRecommendation.BOOK_PROFIT);
        }
    }

    private void applyTargetRule(Set<SecondaryRecommendation> recommendations, Position position) {
        if (distanceToTargetPercent(position).compareTo(TARGET_DISTANCE_THRESHOLD) <= 0) {
            recommendations.add(SecondaryRecommendation.BOOK_PROFIT);
        }
    }

    private void applyStopLossRule(
            Set<SecondaryRecommendation> recommendations,
            Position position,
            FearGreedLevel fearGreedLevel
    ) {
        if (profitPercentage(position).compareTo(PROFIT_THRESHOLD) > 0 && isFearfulMarket(fearGreedLevel)) {
            recommendations.add(SecondaryRecommendation.TIGHTEN_STOP_LOSS);
        }
    }

    private void applyHedgeRule(
            Set<SecondaryRecommendation> recommendations,
            PositionHealthReport healthReport,
            FearGreedLevel fearGreedLevel
    ) {
        if (healthReport.getHealthScore() >= 65 && fearGreedLevel == FearGreedLevel.EXTREME_FEAR) {
            recommendations.add(SecondaryRecommendation.HEDGE_POSITION);
        }
    }

    private BigDecimal distanceToTargetPercent(Position position) {
        BigDecimal distanceToTarget = position.getTargetPrice().subtract(position.getCurrentPrice());
        return percentage(distanceToTarget, position.getTargetPrice());
    }

    private BigDecimal profitPercentage(Position position) {
        BigDecimal profit = position.getCurrentPrice().subtract(position.getBuyPrice());
        return percentage(profit, position.getBuyPrice());
    }

    private BigDecimal percentage(BigDecimal value, BigDecimal base) {
        return value.multiply(ONE_HUNDRED).divide(base, CALCULATION_SCALE, RoundingMode.HALF_UP);
    }

    private boolean isFearfulMarket(FearGreedLevel fearGreedLevel) {
        return fearGreedLevel == FearGreedLevel.FEAR
                || fearGreedLevel == FearGreedLevel.EXTREME_FEAR;
    }
}
