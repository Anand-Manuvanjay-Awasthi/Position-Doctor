package org.example.positiondoctor.recommendation.service;

import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.recommendation.dto.RecommendationResponse;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;

import java.util.List;

public interface RecommendationEngineService {

    RecommendationResponse evaluate(
            Position position,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport,
            FundamentalStrengthReport fundamentalStrengthReport
    );

    PrimaryRecommendation evaluatePrimaryRecommendation(
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    );

    List<SecondaryRecommendation> evaluateSecondaryRecommendations(
            Position position,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    );

    int calculateConfidence(PositionHealthReport healthReport, MarketContextReport marketContextReport);

    String generateRationale(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    );

    RecommendationResponse buildResponse(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            int confidence,
            String rationale,
            FundamentalStrengthReport fundamentalStrengthReport,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    );
}
