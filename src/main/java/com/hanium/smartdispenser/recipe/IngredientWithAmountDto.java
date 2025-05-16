package com.hanium.smartdispenser.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IngredientWithAmountDto {

    private final Long ingredientId;
    private final int amount;

    @JsonCreator
    public IngredientWithAmountDto(
            @JsonProperty("ingredientId") Long ingredientId,
            @JsonProperty("amount") int amount) {
        this.ingredientId = ingredientId;
        this.amount = amount;
    }
}
