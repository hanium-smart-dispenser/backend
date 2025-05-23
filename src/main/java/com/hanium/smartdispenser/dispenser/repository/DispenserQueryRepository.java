package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;

public interface DispenserQueryRepository {
    Dispenser findByUser(Long userId);
}
