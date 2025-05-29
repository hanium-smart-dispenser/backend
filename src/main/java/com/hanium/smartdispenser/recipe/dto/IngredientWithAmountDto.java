package com.hanium.smartdispenser.recipe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class IngredientWithAmountDto {

    @NotNull
    private final Long ingredientId;

    private final int amount;
    private final IngredientType type;

    @JsonCreator
    public IngredientWithAmountDto(
            @JsonProperty("ingredientId") Long ingredientId,
            @JsonProperty("amount") int amount,
            @JsonProperty("type") IngredientType type) {
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.type = type;
    }

}
