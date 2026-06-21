package org.example.positiondoctor.marketcontext.mapper;

import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.entity.MarketContextSnapshot;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;
import org.springframework.stereotype.Component;

@Component
public class MarketContextMapper {

    public MarketContextSnapshot toEntity(Integer fearGreedIndex, FearGreedLevel fearGreedLevel) {
        return MarketContextSnapshot.builder()
                .fearGreedIndex(fearGreedIndex)
                .fearGreedLevel(fearGreedLevel)
                .build();
    }

    public MarketContextReport toReport(MarketContextSnapshot snapshot) {
        return MarketContextReport.builder()
                .id(snapshot.getId())
                .fearGreedIndex(snapshot.getFearGreedIndex())
                .fearGreedLevel(snapshot.getFearGreedLevel())
                .createdAt(snapshot.getCreatedAt())
                .build();
    }
}
