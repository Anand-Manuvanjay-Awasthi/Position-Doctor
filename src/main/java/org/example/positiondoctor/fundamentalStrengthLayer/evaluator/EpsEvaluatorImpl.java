package org.example.positiondoctor.fundamentalStrengthLayer.evaluator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class EpsEvaluatorImpl implements EpsEvaluator {

    @Override
    public int evaluate(BigDecimal eps) {
        Objects.requireNonNull(eps, "eps must not be null");

        if (eps.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        if (eps.compareTo(BigDecimal.ONE) < 0) {
            return 15;
        }
        if (eps.compareTo(BigDecimal.valueOf(3)) < 0) {
            return 30;
        }
        if (eps.compareTo(BigDecimal.valueOf(5)) < 0) {
            return 40;
        }

        return 50;
    }
}
