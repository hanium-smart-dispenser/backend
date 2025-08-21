package com.hanium.smartdispenser.ingredient.repository;

import com.hanium.smartdispenser.ingredient.domain.IngredientSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientSnapshotRepository extends JpaRepository<IngredientSnapshot,Long> {

    Optional<IngredientSnapshot> findByRecipe_Id(Long recipeId);
}
