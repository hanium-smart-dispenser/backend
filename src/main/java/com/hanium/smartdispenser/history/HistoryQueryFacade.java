package com.hanium.smartdispenser.history;

import com.hanium.smartdispenser.dispenser.service.DispenserService;
import com.hanium.smartdispenser.history.dto.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryQueryFacade {

    private final HistoryService historyService;
    private final DispenserService dispenserService;

    public Page<HistoryResponseDto> getHistoriesByUser(Long userId, Pageable pageable) {
        return historyService.getHistoriesByUser(userId, pageable).map(HistoryResponseDto::of);
    }

    public Page<HistoryResponseDto> getHistoriesByUserAndDispenser(Long userId, Long dispenserId, Pageable pageable) {
        dispenserService.validateUserAccess(userId, dispenserId);
        return historyService.getHistoriesByUserAndDispenser(userId, dispenserId, pageable).map(HistoryResponseDto::of);
    }
}
