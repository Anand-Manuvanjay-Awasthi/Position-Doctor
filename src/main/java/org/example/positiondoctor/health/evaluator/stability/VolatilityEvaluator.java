package org.example.positiondoctor.health.evaluator.stability;

import org.example.positiondoctor.health.dto.PositionHealthRequest;

public interface VolatilityEvaluator {

    int evaluate(PositionHealthRequest request);
}
