package org.example.positiondoctor.marketcontext.evaluator;

import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;

public interface FearGreedEvaluator {

    FearGreedLevel evaluate(Integer fearGreedIndex);
}
