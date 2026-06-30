package org.example.positiondoctor.realtime.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.Repository.PositionRepository;
import org.example.positiondoctor.alert.service.AlertService;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.fundamentalStrengthLayer.service.FundamentalStrengthService;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.history.dto.PositionHealthSnapshotResponse;
import org.example.positiondoctor.health.history.service.HealthHistoryService;
import org.example.positiondoctor.health.service.HealthScoreService;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.service.MarketContextService;
import org.example.positiondoctor.recommendation.dto.RecommendationResponse;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.service.RecommendationEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealtimeUpdateServiceImpl implements RealtimeUpdateService {

    private static final Logger log = LoggerFactory.getLogger(RealtimeUpdateServiceImpl.class);

    private final PositionRepository positionRepository;
    private final HealthScoreService healthScoreService;
    private final MarketContextService marketContextService;
    private final FundamentalStrengthService fundamentalStrengthService;
    private final RecommendationEngineService recommendationEngineService;
    private final HealthHistoryService healthHistoryService;
    private final AlertService alertService;

    @Override
    public void refreshAllPositions() {
        List<Position> positions = positionRepository.findByActiveTrueOrActiveIsNull();
        log.info("Refreshing {} active positions", positions.size());

        positions.forEach(this::refreshPositionSafely);
    }

    private void refreshPositionSafely(Position position) {
        try {
            refreshPosition(position);
        } catch (RuntimeException exception) {
            log.warn("Failed to refresh position id={}", position.getId(), exception);
        }
    }

    private void refreshPosition(Position position) {
        Optional<PositionHealthSnapshotResponse> previousSnapshot = healthHistoryService.findLatestSnapshot(position.getId());
        PositionHealthReport healthReport = healthScoreService.evaluate(position);
        MarketContextReport marketContextReport = marketContextService.getLatestMarketContext();
        FundamentalStrengthReport fundamentalStrengthReport = fundamentalStrengthService.evaluate(position.getStockSymbol());
        RecommendationResponse recommendation = recommendationEngineService.evaluate(
                position,
                healthReport,
                marketContextReport,
                fundamentalStrengthReport
        );

        healthHistoryService.saveSnapshot(
                position.getId(),
                healthReport,
                recommendation.getPrimaryRecommendation().name(),
                recommendation.getConfidence()
        );
        createAlertIfPrimaryRecommendationChanged(position, previousSnapshot, recommendation);
    }

    private void createAlertIfPrimaryRecommendationChanged(
            Position position,
            Optional<PositionHealthSnapshotResponse> previousSnapshot,
            RecommendationResponse recommendation
    ) {
        previousPrimaryRecommendation(previousSnapshot)
                .filter(previousRecommendation -> previousRecommendation != recommendation.getPrimaryRecommendation())
                .ifPresent(previousRecommendation -> createRecommendationAlert(
                        position,
                        previousRecommendation,
                        recommendation.getPrimaryRecommendation()
                ));
    }

    private Optional<PrimaryRecommendation> previousPrimaryRecommendation(
            Optional<PositionHealthSnapshotResponse> previousSnapshot
    ) {
        return previousSnapshot
                .map(PositionHealthSnapshotResponse::getPrimaryRecommendation)
                .filter(primaryRecommendation -> !primaryRecommendation.isBlank())
                .flatMap(this::parsePrimaryRecommendation);
    }

    private Optional<PrimaryRecommendation> parsePrimaryRecommendation(String primaryRecommendation) {
        try {
            return Optional.of(PrimaryRecommendation.valueOf(primaryRecommendation));
        } catch (IllegalArgumentException exception) {
            log.warn("Ignoring unknown previous primary recommendation: {}", primaryRecommendation);
            return Optional.empty();
        }
    }

    private void createRecommendationAlert(
            Position position,
            PrimaryRecommendation previousRecommendation,
            PrimaryRecommendation currentRecommendation
    ) {
        alertService.createAlert(
                position.getId(),
                position.getStockSymbol(),
                previousRecommendation,
                currentRecommendation
        );
    }
}
