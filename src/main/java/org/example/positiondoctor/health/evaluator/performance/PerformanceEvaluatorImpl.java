package org.example.positiondoctor.health.evaluator.performance;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceEvaluatorImpl implements PerformanceEvaluator {

    private static final int MAX_PERFORMANCE_SCORE = 40;

    private final PnlEvaluator pnlEvaluator;
    private final TargetDistanceEvaluator targetDistanceEvaluator;

    @Override
    public PerformanceEvaluationResult evaluate(PositionHealthRequest request) {
        int pnlContribution = pnlEvaluator.evaluate(request);
        int targetContribution = targetDistanceEvaluator.evaluate(request);
        int performanceScore = ScoreMath.clamp(pnlContribution + targetContribution, MAX_PERFORMANCE_SCORE);

        return PerformanceEvaluationResult.builder()
                .performanceScore(performanceScore)
                .pnlContribution(pnlContribution)
                .targetContribution(targetContribution)
                .build();
    }
}
