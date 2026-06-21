package org.example.positiondoctor.marketcontext.service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.entity.MarketContextSnapshot;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.example.positiondoctor.marketcontext.evaluator.FearGreedEvaluator;
import org.example.positiondoctor.marketcontext.exception.MarketContextNotFoundException;
import org.example.positiondoctor.marketcontext.mapper.MarketContextMapper;
import org.example.positiondoctor.marketcontext.repository.MarketContextSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class MarketContextServiceImpl implements MarketContextService {

    private final FearGreedEvaluator fearGreedEvaluator;
    private final MarketContextSnapshotRepository snapshotRepository;
    private final MarketContextMapper marketContextMapper;

    @Override
    public MarketContextReport recordFearGreedIndex(Integer fearGreedIndex) {
        Objects.requireNonNull(fearGreedIndex, "fear greed index must not be null");

        FearGreedLevel fearGreedLevel = fearGreedEvaluator.evaluate(fearGreedIndex);
        MarketContextSnapshot snapshot = marketContextMapper.toEntity(fearGreedIndex, fearGreedLevel);
        MarketContextSnapshot savedSnapshot = snapshotRepository.save(snapshot);

        return marketContextMapper.toReport(savedSnapshot);
    }

    @Override
    @Transactional(readOnly = true)
    public MarketContextReport getLatestMarketContext() {
        return snapshotRepository.findTopByOrderByCreatedAtDesc()
                .map(marketContextMapper::toReport)
                .orElseThrow(MarketContextNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarketContextReport> getMarketContextHistory() {
        return snapshotRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(marketContextMapper::toReport)
                .toList();
    }
}
