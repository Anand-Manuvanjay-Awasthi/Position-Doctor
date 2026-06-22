package org.example.positiondoctor.fundamentalStrengthLayer.evaluator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class RoeEvaluatorImpl implements RoeEvaluator {

    @Override
    public int evaluate(BigDecimal roe) {
        Objects.requireNonNull(roe, "roe must not be null");

        if (roe.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        if (roe.compareTo(BigDecimal.valueOf(5)) < 0) {
            return 10;
        }
        if (roe.compareTo(BigDecimal.valueOf(10)) < 0) {
            return 25;
        }
        if (roe.compareTo(BigDecimal.valueOf(15)) < 0) {
            return 35;
        }
        if (roe.compareTo(BigDecimal.valueOf(20)) < 0) {
            return 45;
        }

        return 50;
    }
}
