package org.example.positiondoctor.fundamentalStrengthLayer.dto;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;

import java.math.BigDecimal;

@Value
@Builder
public class FundamentalStrengthReport {

    int strengthScore;

    StrengthLevel strengthLevel;

    BigDecimal eps;

    BigDecimal roe;

    String explanation;
}
