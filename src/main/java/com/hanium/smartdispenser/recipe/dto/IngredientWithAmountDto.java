package com.hanium.smartdispenser.recipe.dto;

import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.recipe.domain.Recipe;

import java.util.List;

public record IngredientWithAmountDto (
        Long ingredientId,
        IngredientType type,
        int amount
) {

    public static List<IngredientWithAmountDto> getListToRecipe(Recipe recipe) {
        return recipe.getRecipeIngredientList().stream().map(
                rc -> new IngredientWithAmountDto(
                        rc.getIngredient().getId(), rc.getIngredient().getType(), rc.getAmount())
        ).toList();
    }

}
