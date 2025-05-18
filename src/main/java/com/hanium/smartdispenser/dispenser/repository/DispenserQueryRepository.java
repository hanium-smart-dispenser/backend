package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DispenserQueryRepository {
    Page<Dispenser> findAllByUserIdWithPaging(Long userId, Pageable pageable);
}
