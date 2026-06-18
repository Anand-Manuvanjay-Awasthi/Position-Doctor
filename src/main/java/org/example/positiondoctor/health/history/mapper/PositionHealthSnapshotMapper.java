package org.example.positiondoctor.health.history.mapper;

import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.history.dto.PositionHealthSnapshotResponse;
import org.example.positiondoctor.health.history.entity.PositionHealthSnapshot;
import org.springframework.stereotype.Component;

@Component
public class PositionHealthSnapshotMapper {

    public PositionHealthSnapshot toEntity(Long positionId, PositionHealthReport report) {
        return PositionHealthSnapshot.builder()
                .positionId(positionId)
                .healthScore(report.getHealthScore())
                .riskScore(report.getRiskScore())
                .performanceScore(report.getPerformanceScore())
                .stabilityScore(report.getStabilityScore())
                .healthStatus(report.getHealthStatus())
                .riskLevel(report.getRiskLevel())
                .fluctuationLevel(report.getFluctuationLevel())
                .build();
    }

    public PositionHealthSnapshotResponse toResponse(PositionHealthSnapshot snapshot) {
        return PositionHealthSnapshotResponse.builder()
                .id(snapshot.getId())
                .positionId(snapshot.getPositionId())
                .healthScore(snapshot.getHealthScore())
                .riskScore(snapshot.getRiskScore())
                .performanceScore(snapshot.getPerformanceScore())
                .stabilityScore(snapshot.getStabilityScore())
                .healthStatus(snapshot.getHealthStatus())
                .riskLevel(snapshot.getRiskLevel())
                .fluctuationLevel(snapshot.getFluctuationLevel())
                .createdAt(snapshot.getCreatedAt())
                .build();
    }
}
