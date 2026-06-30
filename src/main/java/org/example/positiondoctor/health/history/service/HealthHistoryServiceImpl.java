package org.example.positiondoctor.health.history.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.history.dto.PositionHealthSnapshotResponse;
import org.example.positiondoctor.health.history.entity.PositionHealthSnapshot;
import org.example.positiondoctor.health.history.exception.HealthSnapshotNotFoundException;
import org.example.positiondoctor.health.history.mapper.PositionHealthSnapshotMapper;
import org.example.positiondoctor.health.history.repository.PositionHealthSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HealthHistoryServiceImpl implements HealthHistoryService {

    private final PositionHealthSnapshotRepository snapshotRepository;
    private final PositionHealthSnapshotMapper snapshotMapper;

    @Override
    public PositionHealthSnapshotResponse saveSnapshot(Long positionId, PositionHealthReport report) {
        return saveSnapshot(positionId, report, null);
    }

    @Override
    public PositionHealthSnapshotResponse saveSnapshot(
            Long positionId,
            PositionHealthReport report,
            String primaryRecommendation
    ) {
        return saveSnapshot(positionId, report, primaryRecommendation, null);
    }

    @Override
    public PositionHealthSnapshotResponse saveSnapshot(
            Long positionId,
            PositionHealthReport report,
            String primaryRecommendation,
            Integer recommendationConfidence
    ) {
        Objects.requireNonNull(positionId, "position id must not be null");
        Objects.requireNonNull(report, "position health report must not be null");

        PositionHealthSnapshot snapshot = snapshotMapper.toEntity(
                positionId,
                report,
                primaryRecommendation,
                recommendationConfidence
        );
        PositionHealthSnapshot savedSnapshot = snapshotRepository.save(snapshot);

        return snapshotMapper.toResponse(savedSnapshot);
    }

    @Override
    @Transactional(readOnly = true)
    public PositionHealthSnapshotResponse getLatestSnapshot(Long positionId) {
        Objects.requireNonNull(positionId, "position id must not be null");

        return findLatestSnapshot(positionId)
                .orElseThrow(() -> new HealthSnapshotNotFoundException(positionId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PositionHealthSnapshotResponse> findLatestSnapshot(Long positionId) {
        Objects.requireNonNull(positionId, "position id must not be null");

        return snapshotRepository.findTopByPositionIdOrderByCreatedAtDesc(positionId)
                .map(snapshotMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionHealthSnapshotResponse> getHistory(Long positionId) {
        Objects.requireNonNull(positionId, "position id must not be null");

        return snapshotRepository.findByPositionIdOrderByCreatedAtDesc(positionId)
                .stream()
                .map(snapshotMapper::toResponse)
                .toList();
    }
}
