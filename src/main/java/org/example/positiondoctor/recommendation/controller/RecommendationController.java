package org.example.positiondoctor.recommendation.controller;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.Repository.PositionRepository;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.exception.PositionNotFoundException;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.fundamentalStrengthLayer.service.FundamentalStrengthService;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.service.HealthScoreService;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.service.MarketContextService;
import org.example.positiondoctor.recommendation.dto.RecommendationResponse;
import org.example.positiondoctor.recommendation.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final PositionRepository positionRepository;
    private final HealthScoreService healthScoreService;
    private final MarketContextService marketContextService;
    private final FundamentalStrengthService fundamentalStrengthService;
    private final RecommendationEngineService recommendationEngineService;

    @GetMapping("/{positionId}")
    public ResponseEntity<RecommendationResponse> getRecommendation(@PathVariable Long positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new PositionNotFoundException(positionId));

        PositionHealthReport healthReport = healthScoreService.evaluate(position);
        MarketContextReport marketContextReport = marketContextService.getLatestMarketContext();
        FundamentalStrengthReport fundamentalStrengthReport = fundamentalStrengthService.evaluate(position.getStockSymbol());

        RecommendationResponse response = recommendationEngineService.evaluate(
                position,
                healthReport,
                marketContextReport,
                fundamentalStrengthReport
        );

        return ResponseEntity.ok(response);
    }
}
