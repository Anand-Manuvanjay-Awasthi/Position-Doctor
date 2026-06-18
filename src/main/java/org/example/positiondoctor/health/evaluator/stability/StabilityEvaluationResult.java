package org.example.positiondoctor.health.evaluator.stability;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StabilityEvaluationResult {

    int stabilityScore;

    int volatilityContribution;
}
