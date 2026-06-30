package org.example.positiondoctor.alert.mapper;

import org.example.positiondoctor.alert.dto.AlertResponse;
import org.example.positiondoctor.alert.entity.Alert;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public AlertResponse toResponse(Alert alert) {
        return AlertResponse.builder()
                .stockSymbol(alert.getStockSymbol())
                .previousRecommendation(alert.getPreviousRecommendation())
                .currentRecommendation(alert.getCurrentRecommendation())
                .message(alert.getMessage())
                .createdAt(alert.getCreatedAt())
                .isRead(alert.getIsRead())
                .build();
    }
}
