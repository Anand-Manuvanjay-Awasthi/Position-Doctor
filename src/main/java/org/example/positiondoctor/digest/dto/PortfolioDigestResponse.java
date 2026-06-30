package org.example.positiondoctor.digest.dto;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;

@Value
@Builder
public class PortfolioDigestResponse {

    Integer totalPositions;

    Integer healthyPositions;

    Integer watchCloselyPositions;

    Integer reducePositionRecommendations;

    Integer considerExitRecommendations;

    Double averageHealthScore;

    String highestHealthPosition;

    Integer highestHealthScore;

    String lowestHealthPosition;

    Integer lowestHealthScore;

    String highestConfidencePosition;

    PrimaryRecommendation highestConfidenceRecommendation;

    Integer highestConfidence;

    String lowestConfidencePosition;

    PrimaryRecommendation lowestConfidenceRecommendation;

    Integer lowestConfidence;

    Integer unreadAlerts;
}
