package org.example.positiondoctor.digest.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.Repository.PositionRepository;
import org.example.positiondoctor.alert.service.AlertService;
import org.example.positiondoctor.digest.dto.PortfolioDigestResponse;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.health.history.dto.PositionHealthSnapshotResponse;
import org.example.positiondoctor.health.history.service.HealthHistoryService;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioDigestServiceImpl implements PortfolioDigestService {

    private static final Logger log = LoggerFactory.getLogger(PortfolioDigestServiceImpl.class);

    private final PositionRepository positionRepository;
    private final HealthHistoryService healthHistoryService;
    private final AlertService alertService;

    @Override
    public PortfolioDigestResponse generateDailyDigest() {
        List<Position> activePositions = positionRepository.findByActiveTrueOrActiveIsNull();
        List<DigestItem> digestItems = latestDigestItems(activePositions);

        log.info("Generated portfolio digest for {} active positions", activePositions.size());

        return PortfolioDigestResponse.builder()
                .totalPositions(activePositions.size())
                .healthyPositions(countHealthyPositions(digestItems))
                .watchCloselyPositions(countByRecommendation(digestItems, PrimaryRecommendation.WATCH_CLOSELY))
                .reducePositionRecommendations(countByRecommendation(digestItems, PrimaryRecommendation.REDUCE_POSITION))
                .considerExitRecommendations(countByRecommendation(digestItems, PrimaryRecommendation.CONSIDER_EXIT))
                .averageHealthScore(averageHealthScore(digestItems))
                .highestHealthPosition(stockSymbol(highestHealthItem(digestItems)))
                .highestHealthScore(healthScore(highestHealthItem(digestItems)))
                .lowestHealthPosition(stockSymbol(lowestHealthItem(digestItems)))
                .lowestHealthScore(healthScore(lowestHealthItem(digestItems)))
                .highestConfidencePosition(stockSymbol(highestConfidenceItem(digestItems)))
                .highestConfidenceRecommendation(primaryRecommendation(highestConfidenceItem(digestItems)))
                .highestConfidence(confidence(highestConfidenceItem(digestItems)))
                .lowestConfidencePosition(stockSymbol(lowestConfidenceItem(digestItems)))
                .lowestConfidenceRecommendation(primaryRecommendation(lowestConfidenceItem(digestItems)))
                .lowestConfidence(confidence(lowestConfidenceItem(digestItems)))
                .unreadAlerts(unreadAlertCount())
                .build();
    }

    private List<DigestItem> latestDigestItems(List<Position> positions) {
        return positions.stream()
                .map(this::latestDigestItem)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<DigestItem> latestDigestItem(Position position) {
        return healthHistoryService.findLatestSnapshot(position.getId())
                .map(snapshot -> new DigestItem(
                        position.getStockSymbol(),
                        snapshot,
                        parsePrimaryRecommendation(snapshot.getPrimaryRecommendation()).orElse(null)
                ));
    }

    private Integer countHealthyPositions(List<DigestItem> digestItems) {
        return Math.toIntExact(digestItems.stream()
                .filter(item -> item.primaryRecommendation() == PrimaryRecommendation.STRONG_HOLD
                        || item.primaryRecommendation() == PrimaryRecommendation.HOLD)
                .count());
    }

    private Integer countByRecommendation(
            List<DigestItem> digestItems,
            PrimaryRecommendation primaryRecommendation
    ) {
        return Math.toIntExact(digestItems.stream()
                .filter(item -> item.primaryRecommendation() == primaryRecommendation)
                .count());
    }

    private Double averageHealthScore(List<DigestItem> digestItems) {
        return digestItems.stream()
                .map(DigestItem::snapshot)
                .map(PositionHealthSnapshotResponse::getHealthScore)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    private Optional<DigestItem> highestHealthItem(List<DigestItem> digestItems) {
        return digestItems.stream()
                .max(Comparator.comparingInt(item -> item.snapshot().getHealthScore()));
    }

    private Optional<DigestItem> lowestHealthItem(List<DigestItem> digestItems) {
        return digestItems.stream()
                .min(Comparator.comparingInt(item -> item.snapshot().getHealthScore()));
    }

    private Optional<DigestItem> highestConfidenceItem(List<DigestItem> digestItems) {
        return confidenceItems(digestItems)
                .max(Comparator.comparingInt(item -> item.snapshot().getRecommendationConfidence()));
    }

    private Optional<DigestItem> lowestConfidenceItem(List<DigestItem> digestItems) {
        return confidenceItems(digestItems)
                .min(Comparator.comparingInt(item -> item.snapshot().getRecommendationConfidence()));
    }

    private java.util.stream.Stream<DigestItem> confidenceItems(List<DigestItem> digestItems) {
        return digestItems.stream()
                .filter(item -> item.primaryRecommendation() != null)
                .filter(item -> item.snapshot().getRecommendationConfidence() != null);
    }

    private Optional<PrimaryRecommendation> parsePrimaryRecommendation(String primaryRecommendation) {
        if (primaryRecommendation == null || primaryRecommendation.isBlank()) {
            return Optional.empty();
        }

        try {
            return Optional.of(PrimaryRecommendation.valueOf(primaryRecommendation));
        } catch (IllegalArgumentException exception) {
            log.warn("Ignoring unknown digest primary recommendation: {}", primaryRecommendation);
            return Optional.empty();
        }
    }

    private Integer unreadAlertCount() {
        return alertService.getUnreadAlerts().size();
    }

    private String stockSymbol(Optional<DigestItem> digestItem) {
        return digestItem.map(DigestItem::stockSymbol).orElse(null);
    }

    private Integer healthScore(Optional<DigestItem> digestItem) {
        return digestItem.map(DigestItem::snapshot)
                .map(PositionHealthSnapshotResponse::getHealthScore)
                .orElse(null);
    }

    private Integer confidence(Optional<DigestItem> digestItem) {
        return digestItem.map(DigestItem::snapshot)
                .map(PositionHealthSnapshotResponse::getRecommendationConfidence)
                .orElse(null);
    }

    private PrimaryRecommendation primaryRecommendation(Optional<DigestItem> digestItem) {
        return digestItem.map(DigestItem::primaryRecommendation).orElse(null);
    }

    private record DigestItem(
            String stockSymbol,
            PositionHealthSnapshotResponse snapshot,
            PrimaryRecommendation primaryRecommendation
    ) {
    }
}
