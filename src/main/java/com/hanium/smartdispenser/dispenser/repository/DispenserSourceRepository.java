package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.DispenserSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispenserSourceRepository extends JpaRepository<DispenserSource, Long>, DispenserSourceQueryRepository {
}
