package org.example.positiondoctor.alert.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.positiondoctor.recommendation.enums.PrimaryRecommendation;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "alerts",
        indexes = {
                @Index(name = "idx_alert_position_id", columnList = "position_id"),
                @Index(name = "idx_alert_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_id", nullable = false)
    private Long positionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_recommendation", nullable = false, length = 30)
    private PrimaryRecommendation previousRecommendation;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_recommendation", nullable = false, length = 30)
    private PrimaryRecommendation currentRecommendation;

    @Column(nullable = false, length = 500)
    private String message;

    @Builder.Default
    @Column(nullable = false)
    private Boolean acknowledged = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (acknowledged == null) {
            acknowledged = false;
        }
    }
}
