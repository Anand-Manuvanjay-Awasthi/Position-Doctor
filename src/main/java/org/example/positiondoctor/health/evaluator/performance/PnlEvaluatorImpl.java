package org.example.positiondoctor.health.evaluator.performance;

import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PnlEvaluatorImpl implements PnlEvaluator {

    @Override
    public int evaluate(PositionHealthRequest request) {
        BigDecimal pnl = request.getCurrentPrice().subtract(request.getBuyPrice());
        BigDecimal pnlPercentage = ScoreMath.percentageOf(pnl, request.getBuyPrice());

        if (ScoreMath.greaterThanOrEqual(pnlPercentage, 20)) {
            return 20;
        }
        if (ScoreMath.greaterThanOrEqual(pnlPercentage, 10)) {
            return 16;
        }
        if (ScoreMath.greaterThanOrEqual(pnlPercentage, 5)) {
            return 12;
        }
        if (ScoreMath.greaterThanOrEqual(pnlPercentage, 0)) {
            return 10;
        }
        if (ScoreMath.greaterThanOrEqual(pnlPercentage, -5)) {
            return 6;
        }
        if (ScoreMath.greaterThanOrEqual(pnlPercentage, -10)) {
            return 3;
        }

        return 0;
    }
}
