package org.example.positiondoctor.alert.repository;

import org.example.positiondoctor.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findAllByOrderByCreatedAtDesc();

    @Query("select alert from Alert alert where alert.isRead = false order by alert.createdAt desc")
    List<Alert> findUnreadAlerts();
}
