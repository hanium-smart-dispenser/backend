package com.hanium.smartdispenser.history;

import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import com.hanium.smartdispenser.history.dto.HistoryDto;
import com.hanium.smartdispenser.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryService {

    private final HistoryRepository historyRepository;

    public void saveHistory(History history) {
        historyRepository.save(history);
    }

    public Page<HistoryDto> getHistoriesByUser(Long userId, Pageable pageable) {
        return historyRepository.findAllByUserIdWithPaging(userId, pageable);
    }

    public Page<History> getHistoriesByUserAndDispenser(Long userId, Long dispenserId, Pageable pageable) {
        return historyRepository.findAllByUserIdAndDispenserIdWithPaging(userId, dispenserId, pageable);
    }

    public History findById(Long historyId) {
        return historyRepository.findById(historyId).orElseThrow(() -> new HistoryNotFound(historyId));
    }

    public void updateHistoryStatus(Long historyId, HistoryStatus status) {
        History history = findById(historyId);
        if (status == HistoryStatus.SUCCESS) {
            history.markSuccess();
        } else {
            history.updateStatus(status);
        }
    }
}
