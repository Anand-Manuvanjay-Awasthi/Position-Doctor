package org.example.positiondoctor.marketcontext.dto;

import lombok.Builder;
import lombok.Value;
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;

import java.time.LocalDateTime;

@Value
@Builder
public class MarketContextReport {

    Long id;

    Integer fearGreedIndex;

    FearGreedLevel fearGreedLevel;

    LocalDateTime createdAt;
}
