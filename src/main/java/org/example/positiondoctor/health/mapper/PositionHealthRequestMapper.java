package org.example.positiondoctor.health.mapper;

import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.health.dto.PositionHealthRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PositionHealthRequestMapper {

    /*
     * Position is a snapshot and does not contain market-history signals yet.
     * Keep these adapter defaults outside the evaluators so scoring rules remain isolated.
     */
    private static final BigDecimal NEUTRAL_TREND_PERCENTAGE = BigDecimal.ZERO;
    private static final BigDecimal DEFAULT_VOLATILITY_PERCENTAGE = BigDecimal.TEN;

    public PositionHealthRequest toHealthRequest(Position position) {
        return PositionHealthRequest.builder()
                .buyPrice(position.getBuyPrice())
                .currentPrice(position.getCurrentPrice())
                .targetPrice(position.getTargetPrice())
                .stopLoss(position.getStopLoss())
                .trendPercentage(NEUTRAL_TREND_PERCENTAGE)
                .volatilityPercentage(DEFAULT_VOLATILITY_PERCENTAGE)
                .build();
    }
}
