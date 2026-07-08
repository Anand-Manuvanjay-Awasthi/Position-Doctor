package org.example.positiondoctor.DTO;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;

@Value
@Builder
public class PositionSummaryResponse {

    Long id;

    String stockSymbol;

    Integer healthScore;

    PrimaryRecommendation primaryRecommendation;

    Integer confidence;
}
