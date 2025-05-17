package com.hanium.smartdispenser.dispenser.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.recipe.dto.IngredientWithAmountDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DispenserCommandPayLoadDto {

    private final String commandId;
    private final Long userId;
    private final Long recipeId;
    private final List<IngredientWithAmountDto> ingredients;
    private final LocalDateTime requestedAt;

    @JsonCreator
    public DispenserCommandPayLoadDto(@JsonProperty("commandId") String commandId,
                                      @JsonProperty("userId") Long userId,
                                      @JsonProperty("recipeId") Long recipeId,
                                      @JsonProperty("ingredients") List<IngredientWithAmountDto> ingredients,
                                      @JsonProperty("requestedAt") LocalDateTime requestedAt) {
        this.commandId = commandId;
        this.userId = userId;
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.requestedAt = requestedAt;
    }
}
