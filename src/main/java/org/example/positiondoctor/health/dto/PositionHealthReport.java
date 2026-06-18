package org.example.positiondoctor.health.dto;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.health.enums.FluctuationLevel;
import org.example.positiondoctor.health.enums.HealthStatus;
import org.example.positiondoctor.health.enums.RiskLevel;

@Value
@Builder
public class PositionHealthReport {

    int healthScore;

    HealthStatus healthStatus;

    RiskLevel riskLevel;

    FluctuationLevel fluctuationLevel;

    int riskScore;

    int performanceScore;

    int stabilityScore;

    FactorBreakdown factorBreakdown;
}
