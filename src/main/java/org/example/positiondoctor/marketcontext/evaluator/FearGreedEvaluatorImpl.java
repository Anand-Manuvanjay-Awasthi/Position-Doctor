package org.example.positiondoctor.marketcontext.evaluator;

import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FearGreedEvaluatorImpl implements FearGreedEvaluator {

    @Override
    public FearGreedLevel evaluate(Integer fearGreedIndex) {
        Objects.requireNonNull(fearGreedIndex, "fear greed index must not be null");

        if (fearGreedIndex <= 24) {
            return FearGreedLevel.EXTREME_FEAR;
        }
        if (fearGreedIndex <= 44) {
            return FearGreedLevel.FEAR;
        }
        if (fearGreedIndex <= 55) {
            return FearGreedLevel.NEUTRAL;
        }
        if (fearGreedIndex <= 75) {
            return FearGreedLevel.GREED;
        }

        return FearGreedLevel.EXTREME_GREED;
    }
}
