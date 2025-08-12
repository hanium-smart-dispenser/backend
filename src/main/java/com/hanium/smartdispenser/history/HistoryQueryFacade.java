package com.hanium.smartdispenser.history;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.service.DispenserService;
import com.hanium.smartdispenser.history.dto.HistoryDto;
import com.hanium.smartdispenser.history.dto.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryQueryFacade {

    private final HistoryService historyService;

    public Page<HistoryDto> getHistoriesByUser(Long userId, Pageable pageable) {
        return historyService.getHistoriesByUser(userId, pageable);
    }
}
