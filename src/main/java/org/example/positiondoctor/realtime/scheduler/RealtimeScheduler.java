package org.example.positiondoctor.realtime.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.positiondoctor.realtime.service.RealtimeUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RealtimeScheduler {

    private static final Logger log = LoggerFactory.getLogger(RealtimeScheduler.class);

    private final RealtimeUpdateService realtimeUpdateService;

    @Scheduled(fixedRate = 120000)
    public void refreshPositions() {
        log.info("Starting realtime position refresh");
        try {
            realtimeUpdateService.refreshAllPositions();
            log.info("Finished realtime position refresh");
        } catch (RuntimeException exception) {
            log.error("Realtime position refresh failed", exception);
        }
    }
}
