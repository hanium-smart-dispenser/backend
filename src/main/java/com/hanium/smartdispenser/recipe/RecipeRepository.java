package com.hanium.smartdispenser.recipe;

import com.hanium.smartdispenser.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @EntityGraph(attributePaths = {"recipeIngredientList", "recipeIngredientList.ingredient"})
    @Query("select r from Recipe r where r.id = :id")
    Optional<Recipe> findByIdWithIngredients(@Param("id") Long id);
}
