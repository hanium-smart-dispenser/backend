package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.DispenserSource;

import java.util.List;

public interface DispenserSourceQueryRepository {

    List<DispenserSource> findAllByDispenser(Long dispenserId);
}
