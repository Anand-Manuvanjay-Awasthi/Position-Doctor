package org.example.positiondoctor.fundamentalStrengthLayer.evaluator;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.fundamentalStrengthLayer.entity.FundamentalMetrics;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FundamentalScoreEvaluatorImpl implements FundamentalScoreEvaluator {

    private final EpsEvaluator epsEvaluator;
    private final RoeEvaluator roeEvaluator;

    @Override
    public FundamentalScoreResult evaluate(FundamentalMetrics metrics) {
        Objects.requireNonNull(metrics, "fundamental metrics must not be null");

        int epsContribution = epsEvaluator.evaluate(metrics.getEps());
        int roeContribution = roeEvaluator.evaluate(metrics.getRoe());

        return FundamentalScoreResult.builder()
                .strengthScore(epsContribution + roeContribution)
                .epsContribution(epsContribution)
                .roeContribution(roeContribution)
                .build();
    }
}
