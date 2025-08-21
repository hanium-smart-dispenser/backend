package com.hanium.smartdispenser.ai.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SolidAutoIngredient(
        String type,
        String ingredient,
        String pumpId,
        Double targetG,
        Double perPumpG,
        Integer pumpCounts,
        Double estimatedDeliveredG,
        Double overshootG

) implements AutoIngredient {
}
