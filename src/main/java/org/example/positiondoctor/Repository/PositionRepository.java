package org.example.positiondoctor.Repository;

import org.example.positiondoctor.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    List<Position> findByActiveTrueOrActiveIsNull();
}
