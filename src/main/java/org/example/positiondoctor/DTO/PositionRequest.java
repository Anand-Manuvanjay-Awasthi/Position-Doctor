package org.example.positiondoctor.DTO;

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
public class PositionRequest {

    private String stockSymbol;

    private Integer quantity;

    private BigDecimal buyPrice;

    private BigDecimal currentPrice;

    private BigDecimal targetPrice;

    private BigDecimal stopLoss;
}
