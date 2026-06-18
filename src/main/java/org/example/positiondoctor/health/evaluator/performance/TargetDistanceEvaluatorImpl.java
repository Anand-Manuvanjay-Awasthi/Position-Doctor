package org.example.positiondoctor.health.evaluator.performance;

import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TargetDistanceEvaluatorImpl implements TargetDistanceEvaluator {

    @Override
    public int evaluate(PositionHealthRequest request) {
        BigDecimal remainingToTarget = request.getTargetPrice().subtract(request.getCurrentPrice());

        if (remainingToTarget.compareTo(BigDecimal.ZERO) <= 0) {
            return 20;
        }

        BigDecimal remainingPercentage = ScoreMath.percentageOf(remainingToTarget, request.getCurrentPrice());

        if (ScoreMath.lessThanOrEqual(remainingPercentage, 5)) {
            return 18;
        }
        if (ScoreMath.lessThanOrEqual(remainingPercentage, 10)) {
            return 15;
        }
        if (ScoreMath.lessThanOrEqual(remainingPercentage, 20)) {
            return 10;
        }
        if (ScoreMath.lessThanOrEqual(remainingPercentage, 35)) {
            return 6;
        }

        return 2;
    }
}
