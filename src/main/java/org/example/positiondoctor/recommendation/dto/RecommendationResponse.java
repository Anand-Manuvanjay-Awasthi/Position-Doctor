package org.example.positiondoctor.recommendation.dto;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;

import java.util.List;

@Value
@Builder
public class RecommendationResponse {

    PrimaryRecommendation primaryRecommendation;

    List<SecondaryRecommendation> secondaryRecommendations;

    int confidence;

    String rationale;

    RecommendationExplanation explanation;

    StrengthLevel fundamentalStrength;

    int healthScore;

    FearGreedLevel fearGreedLevel;
}
