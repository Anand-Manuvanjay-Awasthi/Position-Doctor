package org.example.positiondoctor.health.evaluator.risk;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RiskEvaluationResult {

    int riskScore;

    int trendContribution;

    int stopLossContribution;
}
