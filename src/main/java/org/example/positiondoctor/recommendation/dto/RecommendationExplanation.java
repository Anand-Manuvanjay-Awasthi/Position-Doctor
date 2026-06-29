package org.example.positiondoctor.recommendation.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecommendationExplanation {

    String recommendationRationale;

    String companyInsights;
}
