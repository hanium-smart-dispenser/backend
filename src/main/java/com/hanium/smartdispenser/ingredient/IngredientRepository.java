package com.hanium.smartdispenser.ingredient;

import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
