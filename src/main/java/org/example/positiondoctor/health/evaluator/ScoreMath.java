package org.example.positiondoctor.health.evaluator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ScoreMath {

    private static final int SCALE = 6;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private ScoreMath() {
    }

    public static BigDecimal percentageOf(BigDecimal amount, BigDecimal base) {
        if (BigDecimal.ZERO.compareTo(base) == 0) {
            throw new IllegalArgumentException("Base value must not be zero");
        }

        return amount.multiply(ONE_HUNDRED).divide(base, SCALE, RoundingMode.HALF_UP);
    }

    public static boolean greaterThanOrEqual(BigDecimal value, int threshold) {
        return value.compareTo(BigDecimal.valueOf(threshold)) >= 0;
    }

    public static boolean lessThanOrEqual(BigDecimal value, int threshold) {
        return value.compareTo(BigDecimal.valueOf(threshold)) <= 0;
    }

    public static int clamp(int score, int maxScore) {
        return Math.max(0, Math.min(score, maxScore));
    }
}
