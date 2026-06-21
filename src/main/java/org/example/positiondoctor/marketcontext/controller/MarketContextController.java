package org.example.positiondoctor.marketcontext.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.marketcontext.dto.FearGreedIndexRequest;
import org.example.positiondoctor.marketcontext.dto.MarketContextReport;
import org.example.positiondoctor.marketcontext.service.MarketContextService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/market-context")
@RequiredArgsConstructor
public class MarketContextController {

    private final MarketContextService marketContextService;

    @PostMapping("/fear-greed")
    public ResponseEntity<MarketContextReport> recordFearGreedIndex(
            @Valid @RequestBody FearGreedIndexRequest request
    ) {
        MarketContextReport report = marketContextService.recordFearGreedIndex(request.getFearGreedIndex());
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @GetMapping("/latest")
    public ResponseEntity<MarketContextReport> getLatestMarketContext() {
        return ResponseEntity.ok(marketContextService.getLatestMarketContext());
    }

    @GetMapping("/history")
    public ResponseEntity<List<MarketContextReport>> getMarketContextHistory() {
        return ResponseEntity.ok(marketContextService.getMarketContextHistory());
    }
}
