package com.hanium.smartdispenser.favorite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @EntityGraph(attributePaths = {
            "recipe",
            "recipe.recipeIngredientList",
            "recipe.recipeIngredientList.ingredient"})
    Page<Favorite> findByUser_Id(Long userId, Pageable pageable);
}
