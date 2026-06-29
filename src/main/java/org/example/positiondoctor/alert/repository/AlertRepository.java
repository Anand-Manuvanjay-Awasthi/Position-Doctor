package org.example.positiondoctor.alert.repository;

import org.example.positiondoctor.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
