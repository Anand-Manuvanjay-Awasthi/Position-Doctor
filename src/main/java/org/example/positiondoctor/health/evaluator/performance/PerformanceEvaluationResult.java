package org.example.positiondoctor.health.evaluator.performance;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PerformanceEvaluationResult {

    int performanceScore;

    int pnlContribution;

    int targetContribution;
}
