package org.example.positiondoctor.fundamentalStrengthLayer.evaluator;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FundamentalScoreResult {

    int strengthScore;

    int epsContribution;

    int roeContribution;
}
