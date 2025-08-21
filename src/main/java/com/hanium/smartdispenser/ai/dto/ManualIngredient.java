package com.hanium.smartdispenser.ai.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record ManualIngredient(
        String ingredient,
        String amount,
        Boolean manual,
        String type
) {
}
