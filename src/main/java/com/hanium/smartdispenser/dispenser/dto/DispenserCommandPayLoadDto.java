package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.recipe.dto.IngredientWithAmountDto;

import java.time.LocalDateTime;
import java.util.List;

public record DispenserCommandPayLoadDto (
        String commandId,
        Long userId,
        Long recipeId,
        List<IngredientWithAmountDto> ingredients,
        LocalDateTime requestedAt
) {
}
