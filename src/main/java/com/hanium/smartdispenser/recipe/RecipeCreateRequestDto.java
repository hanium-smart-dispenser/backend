package com.hanium.smartdispenser.recipe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.recipe.domain.RecipeIngredient;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeCreateRequestDto {

    private final String name;
    private final List<IngredientWithAmountDto> ingredients;

    @JsonCreator
    public RecipeCreateRequestDto(@JsonProperty("name") String name,
                                  @JsonProperty("ingredients") List<IngredientWithAmountDto> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
