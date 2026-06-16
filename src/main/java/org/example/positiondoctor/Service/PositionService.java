package org.example.positiondoctor.Service;

import org.example.positiondoctor.DTO.PositionRequest;
import org.example.positiondoctor.DTO.PositionResponse;

import java.util.List;

public interface PositionService {

    PositionResponse createPosition(PositionRequest request);

    List<PositionResponse> getAllPositions();

    PositionResponse getPositionById(Long id);

    void deletePosition(Long id);
}
