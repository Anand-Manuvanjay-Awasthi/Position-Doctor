package org.example.positiondoctor.alert.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;

import java.time.LocalDateTime;

@Value
@Builder
public class AlertResponse {

    Long id;

    String stockSymbol;

    PrimaryRecommendation previousRecommendation;

    PrimaryRecommendation currentRecommendation;

    String message;

    LocalDateTime createdAt;

    @JsonProperty("isRead")
    Boolean isRead;
}
