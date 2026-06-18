package org.example.positiondoctor.health.evaluator.risk;

import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TrendEvaluatorImpl implements TrendEvaluator {

    @Override
    public int evaluate(PositionHealthRequest request) {
        BigDecimal trendPercentage = request.getTrendPercentage();

        if (ScoreMath.greaterThanOrEqual(trendPercentage, 5)) {
            return 20;
        }
        if (ScoreMath.greaterThanOrEqual(trendPercentage, 2)) {
            return 16;
        }
        if (ScoreMath.greaterThanOrEqual(trendPercentage, 0)) {
            return 12;
        }
        if (ScoreMath.greaterThanOrEqual(trendPercentage, -2)) {
            return 8;
        }
        if (ScoreMath.greaterThanOrEqual(trendPercentage, -5)) {
            return 4;
        }

        return 0;
    }
}
