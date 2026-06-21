package org.example.positiondoctor.marketcontext.entity;

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
import org.example.positiondoctor.marketcontext.enums.FearGreedLevel;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "market_context_snapshots",
        indexes = {
                @Index(name = "idx_market_context_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketContextSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    @Max(100)
    @Column(name = "fear_greed_index", nullable = false)
    private Integer fearGreedIndex;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fear_greed_level", nullable = false, length = 20)
    private FearGreedLevel fearGreedLevel;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
