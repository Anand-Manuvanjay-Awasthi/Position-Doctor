package org.example.positiondoctor.health.history.repository;

import org.example.positiondoctor.health.history.entity.PositionHealthSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionHealthSnapshotRepository extends JpaRepository<PositionHealthSnapshot, Long> {

    Optional<PositionHealthSnapshot> findTopByPositionIdOrderByCreatedAtDesc(Long positionId);

    List<PositionHealthSnapshot> findByPositionIdOrderByCreatedAtDesc(Long positionId);
}
