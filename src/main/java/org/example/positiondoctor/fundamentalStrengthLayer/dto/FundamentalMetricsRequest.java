package org.example.positiondoctor.fundamentalStrengthLayer.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundamentalMetricsRequest {

    @NotBlank
    @Size(max = 20)
    private String stockSymbol;

    @NotNull
    @Digits(integer = 15, fraction = 4)
    private BigDecimal eps;

    @NotNull
    @Digits(integer = 15, fraction = 4)
    private BigDecimal roe;
}
