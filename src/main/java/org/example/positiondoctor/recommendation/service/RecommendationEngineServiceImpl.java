package org.example.positiondoctor.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.recommendation.confidence.RecommendationConfidenceCalculator;
import org.example.positiondoctor.recommendation.dto.RecommendationResponse;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;
import org.example.positiondoctor.recommendation.enums.SecondaryRecommendation;
import org.example.positiondoctor.recommendation.mapper.RecommendationResponseBuilder;
import org.example.positiondoctor.recommendation.rationale.RecommendationRationaleGenerator;
import org.example.positiondoctor.recommendation.rule.PrimaryRecommendationRuleEngine;
import org.example.positiondoctor.recommendation.rule.SecondaryRecommendationRuleEngine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PrimaryRecommendationRuleEngine primaryRecommendationRuleEngine;
    private final SecondaryRecommendationRuleEngine secondaryRecommendationRuleEngine;
    private final RecommendationConfidenceCalculator confidenceCalculator;
    private final RecommendationRationaleGenerator rationaleGenerator;
    private final RecommendationResponseBuilder responseBuilder;

    @Override
    public RecommendationResponse evaluate(
            Position position,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport,
            FundamentalStrengthReport fundamentalStrengthReport
    ) {
        requireInputs(position, healthReport, marketContextReport, fundamentalStrengthReport);

        PrimaryRecommendation primaryRecommendation = evaluatePrimaryRecommendation(healthReport, marketContextReport);
        List<SecondaryRecommendation> secondaryRecommendations = evaluateSecondaryRecommendations(
                position,
                healthReport,
                marketContextReport
        );
        int confidence = calculateConfidence(healthReport, marketContextReport);
        String rationale = generateRationale(
                primaryRecommendation,
                secondaryRecommendations,
                healthReport,
                marketContextReport
        );

        return buildResponse(
                primaryRecommendation,
                secondaryRecommendations,
                confidence,
                rationale,
                fundamentalStrengthReport,
                healthReport,
                marketContextReport
        );
    }

    @Override
    public PrimaryRecommendation evaluatePrimaryRecommendation(
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        return primaryRecommendationRuleEngine.evaluate(healthReport, marketContextReport);
    }

    @Override
    public List<SecondaryRecommendation> evaluateSecondaryRecommendations(
            Position position,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        return secondaryRecommendationRuleEngine.evaluate(position, healthReport, marketContextReport);
    }

    @Override
    public int calculateConfidence(PositionHealthReport healthReport, MarketContextReport marketContextReport) {
        return confidenceCalculator.calculate(healthReport, marketContextReport);
    }

    @Override
    public String generateRationale(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        return rationaleGenerator.generate(
                primaryRecommendation,
                secondaryRecommendations,
                healthReport,
                marketContextReport
        );
    }

    @Override
    public RecommendationResponse buildResponse(
            PrimaryRecommendation primaryRecommendation,
            List<SecondaryRecommendation> secondaryRecommendations,
            int confidence,
            String rationale,
            FundamentalStrengthReport fundamentalStrengthReport,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport
    ) {
        return responseBuilder.build(
                primaryRecommendation,
                secondaryRecommendations,
                confidence,
                rationale,
                fundamentalStrengthReport,
                healthReport,
                marketContextReport
        );
    }

    private void requireInputs(
            Position position,
            PositionHealthReport healthReport,
            MarketContextReport marketContextReport,
            FundamentalStrengthReport fundamentalStrengthReport
    ) {
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(healthReport, "position health report must not be null");
        Objects.requireNonNull(marketContextReport, "market context report must not be null");
        Objects.requireNonNull(fundamentalStrengthReport, "fundamental strength report must not be null");
    }
}
