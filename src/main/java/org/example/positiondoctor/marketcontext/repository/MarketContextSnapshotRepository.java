package org.example.positiondoctor.marketcontext.repository;

import org.example.positiondoctor.marketcontext.entity.MarketContextSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketContextSnapshotRepository extends JpaRepository<MarketContextSnapshot, Long> {

    Optional<MarketContextSnapshot> findTopByOrderByCreatedAtDesc();

    List<MarketContextSnapshot> findAllByOrderByCreatedAtDesc();
}
