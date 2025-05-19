package com.hanium.smartdispenser.history.repository;

import com.hanium.smartdispenser.history.domain.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryQueryRepository {
    Page<History> findAllByUserIdWithPaging(Long userId, Pageable pageable);

    Page<History> findAllByUserIdAndDispenserIdWithPaging(Long userId, Long dispenserId, Pageable pageable);
}
