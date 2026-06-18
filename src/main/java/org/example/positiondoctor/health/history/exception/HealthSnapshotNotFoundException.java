package org.example.positiondoctor.health.history.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HealthSnapshotNotFoundException extends RuntimeException {

    public HealthSnapshotNotFoundException(Long positionId) {
        super("Health snapshot not found for position id: " + positionId);
    }
}
