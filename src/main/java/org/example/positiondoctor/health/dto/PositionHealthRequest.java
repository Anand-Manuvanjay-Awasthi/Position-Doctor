package org.example.positiondoctor.health.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Percent inputs use human-readable values: 5.5 means 5.5%, not 0.055.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionHealthRequest {

    @NotNull
    @Positive
    private BigDecimal buyPrice;

    @NotNull
    @Positive
    private BigDecimal currentPrice;

    @NotNull
    @Positive
    private BigDecimal targetPrice;

    @NotNull
    @Positive
    private BigDecimal stopLoss;

    @NotNull
    private BigDecimal trendPercentage;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal volatilityPercentage;
}
