package org.example.positiondoctor.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.positiondoctor.health.enums.Trend;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePositionRequest {

    @NotBlank
    @Size(max = 20)
    private String stockSymbol;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    @Digits(integer = 15, fraction = 4)
    private BigDecimal buyPrice;

    @NotNull
    @Positive
    @Digits(integer = 15, fraction = 4)
    private BigDecimal currentPrice;

    @NotNull
    @Positive
    @Digits(integer = 15, fraction = 4)
    private BigDecimal stopLoss;

    @NotNull
    @Positive
    @Digits(integer = 15, fraction = 4)
    private BigDecimal targetPrice;

    @NotNull
    private Trend trend;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer fearGreedIndex;

    @NotNull
    @Digits(integer = 15, fraction = 4)
    private BigDecimal eps;

    @NotNull
    @Digits(integer = 15, fraction = 4)
    private BigDecimal roe;
}
