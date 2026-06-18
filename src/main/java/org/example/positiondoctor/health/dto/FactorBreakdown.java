package org.example.positiondoctor.health.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FactorBreakdown {

    int trendContribution;

    int stopLossContribution;

    int pnlContribution;

    int targetContribution;

    int volatilityContribution;
}
