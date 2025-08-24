package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DispenserRepository extends JpaRepository<Dispenser, Long>{

    @EntityGraph(attributePaths = {"dispenserSauces",
            "dispenserSauces.ingredient"})
    Optional<Dispenser> findByUuidWithSauces(String uuid);

    Optional<Dispenser> findByUuid(String uuid);

    Optional<Dispenser> findByUser_Id(Long userId);

    @EntityGraph(attributePaths = {"dispenserSauces",
            "dispenserSauces.ingredient"})
    Optional<Dispenser> findByIdWithSauces(Long id);
}
