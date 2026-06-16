package org.example.positiondoctor.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponse {

    private Long id;

    private String stockSymbol;

    private Integer quantity;

    private BigDecimal buyPrice;

    private BigDecimal currentPrice;

    private BigDecimal targetPrice;

    private BigDecimal stopLoss;

    private LocalDateTime createdAt;
}
