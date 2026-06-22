package org.example.positiondoctor.fundamentalStrengthLayer.evaluator;

import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;
import org.springframework.stereotype.Component;

@Component
public class StrengthLevelEvaluatorImpl implements StrengthLevelEvaluator {

    @Override
    public StrengthLevel evaluate(int strengthScore) {
        if (strengthScore >= 70) {
            return StrengthLevel.STRONG;
        }
        if (strengthScore >= 40) {
            return StrengthLevel.MODERATE;
        }

        return StrengthLevel.WEAK;
    }
}
