package org.example.positiondoctor.health.service;

import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.health.dto.PositionHealthReport;
import org.example.positiondoctor.health.dto.PositionHealthRequest;

public interface HealthScoreService {

    PositionHealthReport evaluate(Position position);

    PositionHealthReport evaluate(PositionHealthRequest request);
}
