package org.example.positiondoctor.health.evaluator.performance;

import org.example.positiondoctor.health.dto.PositionHealthRequest;

public interface PerformanceEvaluator {

    PerformanceEvaluationResult evaluate(PositionHealthRequest request);
}
