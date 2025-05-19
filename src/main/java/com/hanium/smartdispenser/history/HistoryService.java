package com.hanium.smartdispenser.history;

import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional
    public void createHistory(History history) {
        historyRepository.save(history);
    }

    public Page<History> getHistoriesByUser(Long userId, Pageable pageable) {
        return historyRepository.findAllByUserIdWithPaging(userId, pageable);
    }

    public Page<History> getHistoriesByUserAndDispenser(Long userId, Long dispenserId, Pageable pageable) {
        return historyRepository.findAllByUserIdAndDispenserIdWithPaging(userId, dispenserId, pageable);
    }
}
