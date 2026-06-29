package org.example.positiondoctor.alert.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.alert.entity.Alert;
import org.example.positiondoctor.alert.repository.AlertRepository;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertServiceImpl implements AlertService {

    private static final int MAX_MESSAGE_LENGTH = 500;

    private final AlertRepository alertRepository;

    @Override
    public Alert createRecommendationChangedAlert(
            Long positionId,
            PrimaryRecommendation previousRecommendation,
            PrimaryRecommendation currentRecommendation,
            String rationale
    ) {
        Objects.requireNonNull(positionId, "position id must not be null");
        Objects.requireNonNull(previousRecommendation, "previous recommendation must not be null");
        Objects.requireNonNull(currentRecommendation, "current recommendation must not be null");

        Alert alert = Alert.builder()
                .positionId(positionId)
                .previousRecommendation(previousRecommendation)
                .currentRecommendation(currentRecommendation)
                .message(truncate(buildMessage(previousRecommendation, currentRecommendation, rationale)))
                .build();

        return alertRepository.save(alert);
    }

    private String buildMessage(
            PrimaryRecommendation previousRecommendation,
            PrimaryRecommendation currentRecommendation,
            String rationale
    ) {
        String baseMessage = "Primary recommendation changed from "
                + previousRecommendation
                + " to "
                + currentRecommendation
                + ".";

        if (rationale == null || rationale.isBlank()) {
            return baseMessage;
        }

        return baseMessage + " " + rationale;
    }

    private String truncate(String message) {
        if (message.length() <= MAX_MESSAGE_LENGTH) {
            return message;
        }

        return message.substring(0, MAX_MESSAGE_LENGTH);
    }
}
