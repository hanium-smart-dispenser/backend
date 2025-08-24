package com.hanium.smartdispenser.recipe.dto;

import com.hanium.smartdispenser.ingredient.IngredientDto;
import com.hanium.smartdispenser.recipe.domain.Recipe;

import java.util.List;

public record RecipeDto(
        String recipeName,
        List<IngredientDto> ingredients
) {
    public RecipeDto(Recipe recipe) {
        this(recipe.getName(), recipe.getRecipeIngredientList().stream().map
                (IngredientDto::new).toList());
    }
}
