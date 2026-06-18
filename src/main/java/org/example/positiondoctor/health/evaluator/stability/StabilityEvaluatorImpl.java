package org.example.positiondoctor.health.evaluator.stability;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.ScoreMath;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StabilityEvaluatorImpl implements StabilityEvaluator {

    private static final int MAX_STABILITY_SCORE = 20;

    private final VolatilityEvaluator volatilityEvaluator;

    @Override
    public StabilityEvaluationResult evaluate(PositionHealthRequest request) {
        int volatilityContribution = volatilityEvaluator.evaluate(request);
        int stabilityScore = ScoreMath.clamp(volatilityContribution, MAX_STABILITY_SCORE);

        return StabilityEvaluationResult.builder()
                .stabilityScore(stabilityScore)
                .volatilityContribution(volatilityContribution)
                .build();
    }
}
