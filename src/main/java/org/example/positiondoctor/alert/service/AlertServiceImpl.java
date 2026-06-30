package org.example.positiondoctor.alert.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.alert.dto.AlertResponse;
import org.example.positiondoctor.alert.entity.Alert;
import org.example.positiondoctor.alert.exception.AlertNotFoundException;
import org.example.positiondoctor.alert.mapper.AlertMapper;
import org.example.positiondoctor.alert.repository.AlertRepository;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertServiceImpl implements AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);
    private static final int MAX_MESSAGE_LENGTH = 500;

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    @Override
    public Optional<AlertResponse> createAlert(
            Long positionId,
            String stockSymbol,
            PrimaryRecommendation previousRecommendation,
            PrimaryRecommendation currentRecommendation
    ) {
        Objects.requireNonNull(positionId, "position id must not be null");
        Objects.requireNonNull(stockSymbol, "stock symbol must not be null");
        Objects.requireNonNull(previousRecommendation, "previous recommendation must not be null");
        Objects.requireNonNull(currentRecommendation, "current recommendation must not be null");

        if (previousRecommendation == currentRecommendation) {
            log.debug("Skipping alert for position id={} because recommendation did not change", positionId);
            return Optional.empty();
        }

        Alert alert = Alert.builder()
                .positionId(positionId)
                .stockSymbol(stockSymbol.trim().toUpperCase())
                .previousRecommendation(previousRecommendation)
                .currentRecommendation(currentRecommendation)
                .message(buildMessage(previousRecommendation, currentRecommendation))
                .build();

        Alert savedAlert = alertRepository.save(alert);
        log.info("Created alert for position id={} recommendation change {} -> {}",
                positionId,
                previousRecommendation,
                currentRecommendation);

        return Optional.of(alertMapper.toResponse(savedAlert));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlertResponse> getAllAlerts() {
        return alertRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(alertMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlertResponse> getUnreadAlerts() {
        return alertRepository.findUnreadAlerts()
                .stream()
                .map(alertMapper::toResponse)
                .toList();
    }

    @Override
    public AlertResponse markAsRead(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new AlertNotFoundException(id));

        alert.setIsRead(true);
        Alert savedAlert = alertRepository.save(alert);
        log.info("Marked alert id={} as read", id);

        return alertMapper.toResponse(savedAlert);
    }

    private String buildMessage(
            PrimaryRecommendation previousRecommendation,
            PrimaryRecommendation currentRecommendation
    ) {
        return truncate(switch (currentRecommendation) {
            case STRONG_HOLD -> "Recommendation changed from " + previousRecommendation
                    + " to STRONG_HOLD after improved position health.";
            case HOLD -> "Recommendation changed from " + previousRecommendation
                    + " to HOLD as position conditions have stabilized.";
            case WATCH_CLOSELY -> "Holding recommendation has changed to WATCH_CLOSELY due to declining position health.";
            case REDUCE_POSITION -> "Recommendation updated from " + previousRecommendation
                    + " to REDUCE_POSITION as risk has increased.";
            case CONSIDER_EXIT -> "Recommendation changed from " + previousRecommendation
                    + " to CONSIDER_EXIT as position health has weakened significantly.";
        });
    }

    private String truncate(String message) {
        if (message.length() <= MAX_MESSAGE_LENGTH) {
            return message;
        }

        return message.substring(0, MAX_MESSAGE_LENGTH);
    }
}
