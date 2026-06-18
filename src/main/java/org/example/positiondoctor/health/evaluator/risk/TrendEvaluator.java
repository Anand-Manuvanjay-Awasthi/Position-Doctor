package org.example.positiondoctor.health.evaluator.risk;

import org.example.positiondoctor.health.dto.PositionHealthRequest;

public interface TrendEvaluator {

    int evaluate(PositionHealthRequest request);
}
