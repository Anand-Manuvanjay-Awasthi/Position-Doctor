package org.example.positiondoctor.health.evaluator.risk;

import org.example.positiondoctor.health.dto.PositionHealthRequest;

public interface StopLossDistanceEvaluator {

    int evaluate(PositionHealthRequest request);
}
