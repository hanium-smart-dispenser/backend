package com.hanium.smartdispenser.dispenser.repository;

import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispenserSauceRepository extends JpaRepository<DispenserSauce, Long>, DispenserSauceQueryRepository {

    @EntityGraph(attributePaths = "ingredient")
    List<DispenserSauce> findAllWithIngredientByDispenserId(Long dispenserId);
}
