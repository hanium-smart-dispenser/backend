package com.hanium.smartdispenser.ai.dto;

import java.util.List;

public record AiResponse(
        String name,
        List<AutoIngredient> auto_ingredients,
        List<ManualIngredient> manual_ingredients
) {
}
