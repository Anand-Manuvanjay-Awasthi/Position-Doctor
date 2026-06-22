package org.example.positiondoctor.fundamentalStrengthLayer.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalMetricsRequest;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.fundamentalStrengthLayer.entity.FundamentalMetrics;
import org.example.positiondoctor.fundamentalStrengthLayer.enums.StrengthLevel;
import org.example.positiondoctor.fundamentalStrengthLayer.evaluator.FundamentalExplanationGenerator;
import org.example.positiondoctor.fundamentalStrengthLayer.evaluator.FundamentalScoreEvaluator;
import org.example.positiondoctor.fundamentalStrengthLayer.evaluator.FundamentalScoreResult;
import org.example.positiondoctor.fundamentalStrengthLayer.evaluator.StrengthLevelEvaluator;
import org.example.positiondoctor.fundamentalStrengthLayer.exception.FundamentalMetricsNotFoundException;
import org.example.positiondoctor.fundamentalStrengthLayer.mapper.FundamentalMetricsMapper;
import org.example.positiondoctor.fundamentalStrengthLayer.repository.FundamentalMetricsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class FundamentalStrengthServiceImpl implements FundamentalStrengthService {

    private final FundamentalMetricsRepository fundamentalMetricsRepository;
    private final FundamentalMetricsMapper fundamentalMetricsMapper;
    private final FundamentalScoreEvaluator fundamentalScoreEvaluator;
    private final StrengthLevelEvaluator strengthLevelEvaluator;
    private final FundamentalExplanationGenerator explanationGenerator;

    @Override
    public FundamentalStrengthReport saveMetrics(FundamentalMetricsRequest request) {
        Objects.requireNonNull(request, "fundamental metrics request must not be null");

        FundamentalMetrics metrics = fundamentalMetricsMapper.toEntity(request);
        FundamentalMetrics savedMetrics = fundamentalMetricsRepository.save(metrics);

        return buildReport(savedMetrics);
    }

    @Override
    @Transactional(readOnly = true)
    public FundamentalStrengthReport evaluate(String stockSymbol) {
        return buildReport(getLatestMetrics(stockSymbol));
    }

    @Override
    @Transactional(readOnly = true)
    public FundamentalMetrics getLatestMetrics(String stockSymbol) {
        String normalizedStockSymbol = normalizeStockSymbol(stockSymbol);

        return fundamentalMetricsRepository.findTopByStockSymbolIgnoreCaseOrderByCreatedAtDesc(normalizedStockSymbol)
                .orElseThrow(() -> new FundamentalMetricsNotFoundException(normalizedStockSymbol));
    }

    private FundamentalStrengthReport buildReport(FundamentalMetrics metrics) {
        FundamentalScoreResult scoreResult = fundamentalScoreEvaluator.evaluate(metrics);
        StrengthLevel strengthLevel = strengthLevelEvaluator.evaluate(scoreResult.getStrengthScore());
        String explanation = explanationGenerator.generate(strengthLevel);

        return fundamentalMetricsMapper.toReport(
                metrics,
                scoreResult.getStrengthScore(),
                strengthLevel,
                explanation
        );
    }

    private String normalizeStockSymbol(String stockSymbol) {
        Objects.requireNonNull(stockSymbol, "stock symbol must not be null");
        return stockSymbol.trim().toUpperCase(Locale.US);
    }
}
