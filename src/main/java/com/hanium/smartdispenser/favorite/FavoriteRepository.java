package com.hanium.smartdispenser.favorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @EntityGraph(attributePaths = {
            "recipe",
            "recipe.recipeIngredientList",
            "recipe.recipeIngredientList.ingredient"})
    Page<Favorite> findAllByUser_Id(Long userId, Pageable pageable);

    Optional<Favorite> findByUser_IdAndRecipe_Id(Long userId, Long recipeId);
}
