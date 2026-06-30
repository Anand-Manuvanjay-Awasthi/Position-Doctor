package org.example.positiondoctor.digest.service;

import org.example.positiondoctor.digest.dto.PortfolioDigestResponse;

public interface PortfolioDigestService {

    PortfolioDigestResponse generateDailyDigest();
}
