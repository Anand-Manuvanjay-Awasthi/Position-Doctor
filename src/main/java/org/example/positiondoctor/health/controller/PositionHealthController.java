package org.example.positiondoctor.health.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.Repository.PositionRepository;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.exception.PositionNotFoundException;
import org.example.positiondoctor.health.dto.HealthEvaluationRequest;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.history.dto.PositionHealthSnapshotResponse;
import org.example.positiondoctor.health.history.service.HealthHistoryService;
import org.example.positiondoctor.health.mapper.HealthEvaluationRequestMapper;
import org.example.positiondoctor.health.service.HealthScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class PositionHealthController {

    private final PositionRepository positionRepository;
    private final HealthScoreService healthScoreService;
    private final HealthHistoryService healthHistoryService;
    private final HealthEvaluationRequestMapper healthEvaluationRequestMapper;

    @GetMapping("/{positionId}")
    public ResponseEntity<PositionHealthReport> getPositionHealth(@PathVariable Long positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new PositionNotFoundException(positionId));

        PositionHealthReport report = healthScoreService.evaluate(position);
        healthHistoryService.saveSnapshot(positionId, report);

        return ResponseEntity.ok(report);
    }

    @GetMapping("/{positionId}/history")
    public ResponseEntity<List<PositionHealthSnapshotResponse>> getHealthHistory(@PathVariable Long positionId) {
        return ResponseEntity.ok(healthHistoryService.getHistory(positionId));
    }

    @GetMapping("/{positionId}/latest")
    public ResponseEntity<PositionHealthSnapshotResponse> getLatestHealthSnapshot(@PathVariable Long positionId) {
        return ResponseEntity.ok(healthHistoryService.getLatestSnapshot(positionId));
    }

    @PostMapping("/evaluate")
    public ResponseEntity<PositionHealthReport> evaluatePosition(
            @Valid @RequestBody HealthEvaluationRequest request
    ) {
        Position position = healthEvaluationRequestMapper.toPosition(request);
        return ResponseEntity.ok(healthScoreService.evaluate(position));
    }
}
