package org.example.positiondoctor.health.evaluator.risk;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RiskEvaluatorImpl implements RiskEvaluator {

    private static final int MAX_RISK_SCORE = 40;

    private final TrendEvaluator trendEvaluator;
    private final StopLossDistanceEvaluator stopLossDistanceEvaluator;

    @Override
    public RiskEvaluationResult evaluate(PositionHealthRequest request) {
        int trendContribution = trendEvaluator.evaluate(request);
        int stopLossContribution = stopLossDistanceEvaluator.evaluate(request);
        int riskScore = ScoreMath.clamp(trendContribution + stopLossContribution, MAX_RISK_SCORE);

        return RiskEvaluationResult.builder()
                .riskScore(riskScore)
                .trendContribution(trendContribution)
                .stopLossContribution(stopLossContribution)
                .build();
    }
}
