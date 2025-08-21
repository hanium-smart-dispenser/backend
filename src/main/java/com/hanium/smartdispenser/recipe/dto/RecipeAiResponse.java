package com.hanium.smartdispenser.recipe.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.hanium.smartdispenser.ai.dto.AutoIngredient;

import java.util.List;

public record RecipeAiResponse(
        List<AutoIngredient> autoIngredients,
        JsonNode manualIngredients
) {
}
