package org.example.positiondoctor.Service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.DTO.CreatePositionRequest;
import org.example.positiondoctor.DTO.PositionRequest;
import org.example.positiondoctor.DTO.PositionResponse;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalMetricsRequest;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.fundamentalStrengthLayer.service.FundamentalStrengthService;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.example.positiondoctor.health.history.service.HealthHistoryService;
import org.example.positiondoctor.health.mapper.PositionHealthRequestMapper;
import org.example.positiondoctor.health.service.HealthScoreService;
import org.example.positiondoctor.marketcontext.dto.FearGreedIndexRequest;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.service.MarketContextService;
import org.example.positiondoctor.recommendation.dto.RecommendationResponse;
import org.example.positiondoctor.recommendation.service.RecommendationEngineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PositionFacadeServiceImpl implements PositionFacadeService {

    private final PositionService positionService;
    private final MarketContextService marketContextService;
    private final FundamentalStrengthService fundamentalStrengthService;
    private final HealthScoreService healthScoreService;
    private final RecommendationEngineService recommendationEngineService;
    private final HealthHistoryService healthHistoryService;
    private final PositionHealthRequestMapper positionHealthRequestMapper;

    @Override
    public PositionResponse createPosition(CreatePositionRequest request) {
        Objects.requireNonNull(request, "create position request must not be null");

        PositionResponse positionResponse = positionService.createPosition(toPositionRequest(request));
        Position position = toPosition(positionResponse);
        MarketContextReport marketContextReport = recordMarketContext(request);
        FundamentalStrengthReport fundamentalStrengthReport = saveFundamentalMetrics(request);
        PositionHealthReport healthReport = evaluateHealth(position, request);
        RecommendationResponse recommendation = recommendationEngineService.evaluate(
                position,
                healthReport,
                marketContextReport,
                fundamentalStrengthReport
        );

        saveHealthSnapshot(positionResponse.getId(), healthReport, recommendation);

        return positionResponse;
    }

    private PositionRequest toPositionRequest(CreatePositionRequest request) {
        return PositionRequest.builder()
                .stockSymbol(request.getStockSymbol())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .currentPrice(request.getCurrentPrice())
                .targetPrice(request.getTargetPrice())
                .stopLoss(request.getStopLoss())
                .build();
    }

    private MarketContextReport recordMarketContext(CreatePositionRequest request) {
        FearGreedIndexRequest fearGreedIndexRequest = toFearGreedIndexRequest(request);
        return marketContextService.recordFearGreedIndex(fearGreedIndexRequest.getFearGreedIndex());
    }

    private FearGreedIndexRequest toFearGreedIndexRequest(CreatePositionRequest request) {
        return FearGreedIndexRequest.builder()
                .fearGreedIndex(request.getFearGreedIndex())
                .build();
    }

    private FundamentalStrengthReport saveFundamentalMetrics(CreatePositionRequest request) {
        return fundamentalStrengthService.saveMetrics(toFundamentalMetricsRequest(request));
    }

    private FundamentalMetricsRequest toFundamentalMetricsRequest(CreatePositionRequest request) {
        return FundamentalMetricsRequest.builder()
                .stockSymbol(request.getStockSymbol())
                .eps(request.getEps())
                .roe(request.getRoe())
                .build();
    }

    private PositionHealthReport evaluateHealth(Position position, CreatePositionRequest request) {
        PositionHealthRequest healthRequest = positionHealthRequestMapper.toHealthRequest(
                position,
                request.getTrend()
        );
        return healthScoreService.evaluate(healthRequest);
    }

    private void saveHealthSnapshot(
            Long positionId,
            PositionHealthReport healthReport,
            RecommendationResponse recommendation
    ) {
        healthHistoryService.saveSnapshot(
                positionId,
                healthReport,
                recommendation.getPrimaryRecommendation().name(),
                recommendation.getConfidence()
        );
    }

    private Position toPosition(PositionResponse response) {
        return Position.builder()
                .id(response.getId())
                .stockSymbol(response.getStockSymbol())
                .quantity(response.getQuantity())
                .buyPrice(response.getBuyPrice())
                .currentPrice(response.getCurrentPrice())
                .targetPrice(response.getTargetPrice())
                .stopLoss(response.getStopLoss())
                .createdAt(response.getCreatedAt())
                .build();
    }
}
