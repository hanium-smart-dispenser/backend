package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DispenserRepository extends JpaRepository<Dispenser, Long>{

    @EntityGraph(attributePaths = {"dispenserSauces", "dispenserSauces.ingredient"})
    @Query("select d from Dispenser d where d.uuid = :uuid")
    Optional<Dispenser> findByUuidWithSauces(String uuid);

    Optional<Dispenser> findByUuid(String uuid);

    Optional<Dispenser> findByUser_Id(Long userId);

    @EntityGraph(attributePaths = {"dispenserSauces", "dispenserSauces.ingredient"})
    @Query("select d from Dispenser d where d.id = :id")
    Optional<Dispenser> findByIdWithSauces(Long id);

    boolean existsByUuid(String uuid);
}
