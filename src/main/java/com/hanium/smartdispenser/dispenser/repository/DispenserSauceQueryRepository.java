package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;

import java.util.List;

public interface DispenserSauceQueryRepository {

    List<DispenserSauce> findAllByDispenser(Long dispenserId);
}
