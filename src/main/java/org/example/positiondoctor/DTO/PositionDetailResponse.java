package org.example.positiondoctor.DTO;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;
import org.example.positiondoctor.health.enums.FluctuationLevel;
import org.example.positiondoctor.health.enums.HealthStatus;
import org.example.positiondoctor.health.enums.RiskLevel;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class PositionDetailResponse {

    Long id;

    String stockSymbol;

    Integer quantity;

    BigDecimal buyPrice;

    BigDecimal currentPrice;

    BigDecimal stopLoss;

    BigDecimal targetPrice;

    Integer healthScore;

    HealthStatus healthStatus;

    RiskLevel riskLevel;

    FluctuationLevel fluctuationLevel;

    Integer riskScore;

    Integer performanceScore;

    Integer stabilityScore;

    Integer fearGreedIndex;

    FearGreedLevel fearGreedLevel;

    BigDecimal eps;

    BigDecimal roe;

    StrengthLevel fundamentalStrength;

    PrimaryRecommendation primaryRecommendation;

    List<SecondaryRecommendation> secondaryRecommendations;

    Integer confidence;

    String rationale;

    String companyInsights;
}
