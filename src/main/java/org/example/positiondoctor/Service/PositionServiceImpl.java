package org.example.positiondoctor.Service;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.DTO.PositionRequest;
import org.example.positiondoctor.DTO.PositionResponse;
import org.example.positiondoctor.Repository.PositionRepository;
import org.example.positiondoctor.entities.Position;
import org.example.positiondoctor.exception.PositionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public PositionResponse createPosition(PositionRequest request) {
        Position position = Position.builder()
                .stockSymbol(request.getStockSymbol())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .currentPrice(request.getCurrentPrice())
                .targetPrice(request.getTargetPrice())
                .stopLoss(request.getStopLoss())
                .build();

        Position savedPosition = positionRepository.save(position);
        return mapToResponse(savedPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionResponse> getAllPositions() {
        return positionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PositionResponse getPositionById(Long id) {
        return positionRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> positionNotFound(id));
    }

    @Override
    public void deletePosition(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> positionNotFound(id));

        positionRepository.delete(position);
    }

    private PositionResponse mapToResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .stockSymbol(position.getStockSymbol())
                .quantity(position.getQuantity())
                .buyPrice(position.getBuyPrice())
                .currentPrice(position.getCurrentPrice())
                .targetPrice(position.getTargetPrice())
                .stopLoss(position.getStopLoss())
                .createdAt(position.getCreatedAt())
                .build();
    }

    private PositionNotFoundException positionNotFound(Long id) {
        return new PositionNotFoundException(id);
    }
}
