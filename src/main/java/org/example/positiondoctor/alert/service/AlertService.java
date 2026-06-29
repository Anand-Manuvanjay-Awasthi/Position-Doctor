package org.example.positiondoctor.alert.service;

import org.example.positiondoctor.alert.entity.Alert;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;

public interface AlertService {

    Alert createRecommendationChangedAlert(
            Long positionId,
            PrimaryRecommendation previousRecommendation,
            PrimaryRecommendation currentRecommendation,
            String rationale
    );
}
