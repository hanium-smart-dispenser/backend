package com.hanium.smartdispenser.history.repository;

import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.dto.HistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryQueryRepository {
    Page<HistoryDto> findAllByUserIdWithPaging(Long userId, Pageable pageable);

    Page<History> findAllByUserIdAndDispenserIdWithPaging(Long userId, Long dispenserId, Pageable pageable);

}
