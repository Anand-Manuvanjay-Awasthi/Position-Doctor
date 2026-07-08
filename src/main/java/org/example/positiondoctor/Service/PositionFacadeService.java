package org.example.positiondoctor.Service;

import org.example.positiondoctor.DTO.CreatePositionRequest;
import org.example.positiondoctor.DTO.PositionDetailResponse;
import org.example.positiondoctor.DTO.PositionResponse;
import org.example.positiondoctor.DTO.PositionSummaryResponse;

import java.util.List;

public interface PositionFacadeService {

    PositionResponse createPosition(CreatePositionRequest request);

    List<PositionSummaryResponse> getPositionSummaries();

    PositionDetailResponse getPositionDetail(Long id);
}
