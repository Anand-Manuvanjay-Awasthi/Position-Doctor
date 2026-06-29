package org.example.positiondoctor.health.history.service;

import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.history.dto.PositionHealthSnapshotResponse;

import java.util.List;
import java.util.Optional;

public interface HealthHistoryService {

    PositionHealthSnapshotResponse saveSnapshot(Long positionId, PositionHealthReport report);

    PositionHealthSnapshotResponse saveSnapshot(
            Long positionId,
            PositionHealthReport report,
            String primaryRecommendation
    );

    PositionHealthSnapshotResponse getLatestSnapshot(Long positionId);

    Optional<PositionHealthSnapshotResponse> findLatestSnapshot(Long positionId);

    List<PositionHealthSnapshotResponse> getHistory(Long positionId);
}
