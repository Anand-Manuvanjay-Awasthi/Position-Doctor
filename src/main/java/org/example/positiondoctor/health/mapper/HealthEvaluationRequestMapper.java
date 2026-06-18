package org.example.positiondoctor.health.mapper;

import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.health.dto.HealthEvaluationRequest;
import org.springframework.stereotype.Component;

@Component
public class HealthEvaluationRequestMapper {

    public Position toPosition(HealthEvaluationRequest request) {
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
