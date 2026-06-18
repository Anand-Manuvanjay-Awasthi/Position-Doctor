package org.example.positiondoctor.health.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.health.constraint.HealthConstraintProcessor;
import org.example.positiondoctor.health.dto.FactorBreakdown;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.evaluator.performance.PerformanceEvaluationResult;
import org.example.positiondoctor.health.evaluator.performance.PerformanceEvaluator;
import org.example.positiondoctor.health.evaluator.risk.RiskEvaluationResult;
import org.example.positiondoctor.health.evaluator.risk.RiskEvaluator;
import org.example.positiondoctor.health.evaluator.stability.StabilityEvaluationResult;
import org.example.positiondoctor.health.evaluator.stability.StabilityEvaluator;
import org.example.positiondoctor.health.mapper.HealthLevelMapper;
import org.example.positiondoctor.health.mapper.PositionHealthRequestMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HealthScoreServiceImpl implements HealthScoreService {

    private final RiskEvaluator riskEvaluator;
    private final PerformanceEvaluator performanceEvaluator;
    private final StabilityEvaluator stabilityEvaluator;
    private final HealthConstraintProcessor constraintProcessor;
    private final HealthLevelMapper healthLevelMapper;
    private final PositionHealthRequestMapper positionHealthRequestMapper;

    @Override
    public PositionHealthReport evaluate(Position position) {
        Objects.requireNonNull(position, "position must not be null");
        return evaluate(positionHealthRequestMapper.toHealthRequest(position));
    }

    @Override
    public PositionHealthReport evaluate(PositionHealthRequest request) {
        Objects.requireNonNull(request, "position health request must not be null");

        RiskEvaluationResult risk = riskEvaluator.evaluate(request);
        PerformanceEvaluationResult performance = performanceEvaluator.evaluate(request);
        StabilityEvaluationResult stability = stabilityEvaluator.evaluate(request);

        int rawHealthScore = risk.getRiskScore()
                + performance.getPerformanceScore()
                + stability.getStabilityScore();

        int healthScore = constraintProcessor.apply(
                request,
                rawHealthScore,
                risk.getRiskScore(),
                performance.getPerformanceScore()
        );

        FactorBreakdown factorBreakdown = FactorBreakdown.builder()
                .trendContribution(risk.getTrendContribution())
                .stopLossContribution(risk.getStopLossContribution())
                .pnlContribution(performance.getPnlContribution())
                .targetContribution(performance.getTargetContribution())
                .volatilityContribution(stability.getVolatilityContribution())
                .build();

        return PositionHealthReport.builder()
                .healthScore(healthScore)
                .healthStatus(healthLevelMapper.toHealthStatus(healthScore))
                .riskLevel(healthLevelMapper.toRiskLevel(risk.getRiskScore()))
                .fluctuationLevel(healthLevelMapper.toFluctuationLevel(stability.getStabilityScore()))
                .riskScore(risk.getRiskScore())
                .performanceScore(performance.getPerformanceScore())
                .stabilityScore(stability.getStabilityScore())
                .factorBreakdown(factorBreakdown)
                .build();
    }
}
