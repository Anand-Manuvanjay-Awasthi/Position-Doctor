package org.example.positiondoctor.health.history.dto;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.health.enums.FluctuationLevel;
import org.example.positiondoctor.health.enums.HealthStatus;
import org.example.positiondoctor.health.enums.RiskLevel;

import java.time.LocalDateTime;

@Value
@Builder
public class PositionHealthSnapshotResponse {

    Long id;

    Long positionId;

    Integer healthScore;

    Integer riskScore;

    Integer performanceScore;

    Integer stabilityScore;

    HealthStatus healthStatus;

    RiskLevel riskLevel;

    FluctuationLevel fluctuationLevel;

    LocalDateTime createdAt;

    String primaryRecommendation;

    Integer recommendationConfidence;
}
