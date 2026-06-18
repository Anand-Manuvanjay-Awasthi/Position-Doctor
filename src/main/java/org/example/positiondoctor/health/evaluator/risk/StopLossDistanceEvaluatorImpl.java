package org.example.positiondoctor.health.evaluator.risk;

import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StopLossDistanceEvaluatorImpl implements StopLossDistanceEvaluator {

    @Override
    public int evaluate(PositionHealthRequest request) {
        BigDecimal distanceFromStopLoss = request.getCurrentPrice().subtract(request.getStopLoss());

        if (distanceFromStopLoss.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        BigDecimal distancePercentage = ScoreMath.percentageOf(distanceFromStopLoss, request.getCurrentPrice());

        if (ScoreMath.greaterThanOrEqual(distancePercentage, 25)) {
            return 20;
        }
        if (ScoreMath.greaterThanOrEqual(distancePercentage, 20)) {
            return 18;
        }
        if (ScoreMath.greaterThanOrEqual(distancePercentage, 15)) {
            return 14;
        }
        if (ScoreMath.greaterThanOrEqual(distancePercentage, 10)) {
            return 10;
        }
        if (ScoreMath.greaterThanOrEqual(distancePercentage, 5)) {
            return 6;
        }

        return 2;
    }
}
