package org.example.positiondoctor.health.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.Repository.PositionRepository;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.exception.PositionNotFoundException;
import org.example.positiondoctor.health.dto.HealthEvaluationRequest;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.service.HealthScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class PositionHealthController {

    private final PositionRepository positionRepository;
    private final HealthScoreService healthScoreService;

    @GetMapping("/{positionId}")
    public ResponseEntity<PositionHealthReport> getPositionHealth(@PathVariable Long positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new PositionNotFoundException(positionId));

        return ResponseEntity.ok(healthScoreService.evaluate(position));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<PositionHealthReport> evaluatePosition(
            @Valid @RequestBody HealthEvaluationRequest request
    ) {
        return ResponseEntity.ok(healthScoreService.evaluate(toPosition(request)));
    }

    private Position toPosition(HealthEvaluationRequest request) {
        return Position.builder()
                .stockSymbol(request.getStockSymbol())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .currentPrice(request.getCurrentPrice())
                .targetPrice(request.getTargetPrice())
                .stopLoss(request.getStopLoss())
                .build();
    }
}
