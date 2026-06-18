package org.example.positiondoctor.health.mapper;

import org.example.positiondoctor.health.enums.FluctuationLevel;
import org.example.positiondoctor.health.enums.HealthStatus;
import org.example.positiondoctor.health.enums.RiskLevel;
import org.springframework.stereotype.Component;

@Component
public class HealthLevelMapper {

    public HealthStatus toHealthStatus(int healthScore) {
        if (healthScore >= 75) {
            return HealthStatus.HEALTHY;
        }
        if (healthScore >= 50) {
            return HealthStatus.WARNING;
        }

        return HealthStatus.CRITICAL;
    }

    public RiskLevel toRiskLevel(int riskScore) {
        if (riskScore >= 30) {
            return RiskLevel.SAFE;
        }
        if (riskScore >= 20) {
            return RiskLevel.WARNING;
        }

        return RiskLevel.CRITICAL;
    }

    public FluctuationLevel toFluctuationLevel(int stabilityScore) {
        if (stabilityScore >= 16) {
            return FluctuationLevel.STABLE;
        }
        if (stabilityScore >= 8) {
            return FluctuationLevel.MODERATE;
        }

        return FluctuationLevel.VOLATILE;
    }
}
