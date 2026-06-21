package org.example.positiondoctor.marketcontext.service;

import org.example.positiondoctor.marketcontext.dto.MarketContextReport;

import java.util.List;

public interface MarketContextService {

    MarketContextReport recordFearGreedIndex(Integer fearGreedIndex);

    MarketContextReport getLatestMarketContext();

    List<MarketContextReport> getMarketContextHistory();
}
