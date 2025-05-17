package com.hanium.smartdispenser.dispenser.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DispenserCommandRequestDto {

    @NotBlank
    private final Long recipeId;

    @JsonCreator
    public DispenserCommandRequestDto(@JsonProperty("recipeId") Long recipeId) {
        this.recipeId = recipeId;
    }
}
