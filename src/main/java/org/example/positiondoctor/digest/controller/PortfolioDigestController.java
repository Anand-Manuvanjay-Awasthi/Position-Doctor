package org.example.positiondoctor.digest.controller;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.digest.dto.PortfolioDigestResponse;
import org.example.positiondoctor.digest.service.PortfolioDigestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/digest")
@RequiredArgsConstructor
public class PortfolioDigestController {

    private final PortfolioDigestService portfolioDigestService;

    @GetMapping
    public ResponseEntity<PortfolioDigestResponse> getDailyDigest() {
        return ResponseEntity.ok(portfolioDigestService.generateDailyDigest());
    }
}
