package org.example.positiondoctor.health.evaluator.stability;

import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VolatilityEvaluatorImpl implements VolatilityEvaluator {

    @Override
    public int evaluate(PositionHealthRequest request) {
        BigDecimal volatilityPercentage = request.getVolatilityPercentage();

        if (ScoreMath.lessThanOrEqual(volatilityPercentage, 2)) {
            return 20;
        }
        if (ScoreMath.lessThanOrEqual(volatilityPercentage, 5)) {
            return 16;
        }
        if (ScoreMath.lessThanOrEqual(volatilityPercentage, 10)) {
            return 12;
        }
        if (ScoreMath.lessThanOrEqual(volatilityPercentage, 15)) {
            return 8;
        }
        if (ScoreMath.lessThanOrEqual(volatilityPercentage, 25)) {
            return 4;
        }

        return 0;
    }
}
