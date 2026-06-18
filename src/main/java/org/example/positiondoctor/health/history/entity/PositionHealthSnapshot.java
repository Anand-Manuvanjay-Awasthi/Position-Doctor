package org.example.positiondoctor.health.history.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.positiondoctor.health.enums.FluctuationLevel;
import org.example.positiondoctor.health.enums.HealthStatus;
import org.example.positiondoctor.health.enums.RiskLevel;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "position_health_snapshots",
        indexes = {
                @Index(name = "idx_health_snapshot_position_id", columnList = "position_id"),
                @Index(name = "idx_health_snapshot_position_created_at", columnList = "position_id, created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionHealthSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "position_id", nullable = false)
    private Long positionId;

    @NotNull
    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private Integer healthScore;

    @NotNull
    @Min(0)
    @Max(40)
    @Column(nullable = false)
    private Integer riskScore;

    @NotNull
    @Min(0)
    @Max(40)
    @Column(nullable = false)
    private Integer performanceScore;

    @NotNull
    @Min(0)
    @Max(20)
    @Column(nullable = false)
    private Integer stabilityScore;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private HealthStatus healthStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RiskLevel riskLevel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FluctuationLevel fluctuationLevel;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
