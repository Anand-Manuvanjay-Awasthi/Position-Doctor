package org.example.positiondoctor.Repository;

import org.example.positiondoctor.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
