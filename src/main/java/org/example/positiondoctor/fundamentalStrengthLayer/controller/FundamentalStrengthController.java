package org.example.positiondoctor.fundamentalStrengthLayer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalMetricsRequest;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.fundamentalStrengthLayer.service.FundamentalStrengthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fundamentals")
@RequiredArgsConstructor
public class FundamentalStrengthController {

    private final FundamentalStrengthService fundamentalStrengthService;

    @PostMapping
    public ResponseEntity<FundamentalStrengthReport> saveMetrics(
            @Valid @RequestBody FundamentalMetricsRequest request
    ) {
        FundamentalStrengthReport report = fundamentalStrengthService.saveMetrics(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @GetMapping("/{stockSymbol}")
    public ResponseEntity<FundamentalStrengthReport> evaluate(@PathVariable String stockSymbol) {
        return ResponseEntity.ok(fundamentalStrengthService.evaluate(stockSymbol));
    }
}
