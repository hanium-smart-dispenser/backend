package com.hanium.smartdispenser.ingredient;

import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.recipe.domain.RecipeIngredient;

public record IngredientDto(
        Long ingredientId,
        String ingredientName,
        IngredientType type,
        int amount
) {
    public IngredientDto(RecipeIngredient ri) {
        this(ri.getIngredient().getId(), ri.getIngredient().getName(),
                ri.getIngredient().getType(), ri.getAmount());
    }
}
