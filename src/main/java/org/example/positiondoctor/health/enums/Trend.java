package org.example.positiondoctor.health.enums;

import java.math.BigDecimal;

public enum Trend {

    STRONG_UPTREND("5.0"),
    UPTREND("2.0"),
    SIDEWAYS("0.0"),
    DOWNTREND("-2.0"),
    STRONG_DOWNTREND("-5.0");

    private final BigDecimal trendPercentage;

    Trend(String trendPercentage) {
        this.trendPercentage = new BigDecimal(trendPercentage);
    }

    public BigDecimal toPercentage() {
        return trendPercentage;
    }
}
