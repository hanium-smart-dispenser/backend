package com.hanium.smartdispenser.favorite;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FavoriteDto {

    private final Long recipeId;

    @JsonCreator
    public FavoriteDto(@JsonProperty("recipeId") Long recipeId) {
        this.recipeId = recipeId;
    }
}
