package org.example.positiondoctor.health.constraint;

import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HealthConstraintProcessor {

    private static final BigDecimal STOP_LOSS_BUFFER_RATE = new BigDecimal("0.10");

    public int apply(PositionHealthRequest request, int healthScore, int riskScore, int performanceScore) {
        int constrainedHealthScore = healthScore;

        if (isNearStopLoss(request)) {
            constrainedHealthScore = Math.min(constrainedHealthScore, 40);
        }
        if (riskScore < 20) {
            constrainedHealthScore = Math.min(constrainedHealthScore, 50);
        }
        if (performanceScore < 20) {
            constrainedHealthScore = Math.min(constrainedHealthScore, 60);
        }

        return constrainedHealthScore;
    }

    private boolean isNearStopLoss(PositionHealthRequest request) {
        BigDecimal buffer = request.getCurrentPrice().multiply(STOP_LOSS_BUFFER_RATE);
        BigDecimal stopLossWithBuffer = request.getStopLoss().add(buffer);

        return request.getCurrentPrice().compareTo(stopLossWithBuffer) <= 0;
    }
}
