package org.example.positiondoctor.fundamentalStrengthLayer.service;

import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalMetricsRequest;
import org.example.positiondoctor.fundamentalStrengthLayer.dto.FundamentalStrengthReport;
import org.example.positiondoctor.fundamentalStrengthLayer.entity.FundamentalMetrics;

public interface FundamentalStrengthService {

    FundamentalStrengthReport saveMetrics(FundamentalMetricsRequest request);

    FundamentalStrengthReport evaluate(String stockSymbol);

    FundamentalMetrics getLatestMetrics(String stockSymbol);
}
