package org.example.positiondoctor.Service;

import org.example.positiondoctor.DTO.CreatePositionRequest;
import org.example.positiondoctor.DTO.PositionResponse;

public interface PositionFacadeService {

    PositionResponse createPosition(CreatePositionRequest request);
}
