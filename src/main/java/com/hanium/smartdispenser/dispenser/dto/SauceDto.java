package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;

public record SauceDto(
        int slot,
        String ingredientName,
        IngredientType type,
        boolean isLow
) {

    public SauceDto(DispenserSauce dispenserSauce) {
        this(dispenserSauce.getSlot(), dispenserSauce.getIngredient().getName(), dispenserSauce.getIngredient().getType(), dispenserSauce.isLow());
    }
}
