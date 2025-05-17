package com.hanium.smartdispenser.recipe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeCreateRequestDto {


    @NotBlank
    private final String name;

    @Valid
    @NotEmpty
    private final List<IngredientWithAmountDto> ingredients;

    @JsonCreator
    public RecipeCreateRequestDto(@JsonProperty("name") String name,
                                  @JsonProperty("ingredients") List<IngredientWithAmountDto> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
