package org.example.positiondoctor.health.history.service;

import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.history.dto.PositionHealthSnapshotResponse;

import java.util.List;

public interface HealthHistoryService {

    PositionHealthSnapshotResponse saveSnapshot(Long positionId, PositionHealthReport report);

    PositionHealthSnapshotResponse getLatestSnapshot(Long positionId);

    List<PositionHealthSnapshotResponse> getHistory(Long positionId);
}
