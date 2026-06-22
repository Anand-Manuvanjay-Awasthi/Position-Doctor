package org.example.positiondoctor.fundamentalStrengthLayer.evaluator;

import org.example.positiondoctor.fundamentalStrengthLayer.entity.FundamentalMetrics;

public interface FundamentalScoreEvaluator {

    FundamentalScoreResult evaluate(FundamentalMetrics metrics);
}
