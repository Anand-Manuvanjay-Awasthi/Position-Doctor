package org.example.positiondoctor.fundamentalStrengthLayer.evaluator;

import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;
import org.springframework.stereotype.Component;

@Component
public class FundamentalExplanationGenerator {

    public String generate(StrengthLevel strengthLevel) {
        return switch (strengthLevel) {
            case STRONG -> "The company demonstrates strong profitability and efficient capital utilization.";
            case MODERATE -> "The company shows acceptable fundamentals, though profitability or capital efficiency could improve.";
            case WEAK -> "The company shows weak fundamentals based on earnings and return on equity.";
        };
    }
}
