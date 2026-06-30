package org.example.positiondoctor.alert.service;

import org.example.positiondoctor.alert.dto.AlertResponse;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;

import java.util.List;
import java.util.Optional;

public interface AlertService {

    Optional<AlertResponse> createAlert(
            Long positionId,
            String stockSymbol,
            PrimaryRecommendation previousRecommendation,
            PrimaryRecommendation currentRecommendation
    );

    List<AlertResponse> getAllAlerts();

    List<AlertResponse> getUnreadAlerts();

    AlertResponse markAsRead(Long id);
}
