package com.hanium.smartdispenser.favorite;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FavoriteRequestDto {

    private final Long recipeId;

    @JsonCreator
    public FavoriteRequestDto(@JsonProperty("recipeId") Long recipeId) {
        this.recipeId = recipeId;
    }
}
