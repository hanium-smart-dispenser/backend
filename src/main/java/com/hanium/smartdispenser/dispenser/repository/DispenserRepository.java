package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DispenserRepository extends JpaRepository<Dispenser, Long>, DispenserQueryRepository {

    Dispenser findByUuid(@Param("uuid") String uuid);
}
