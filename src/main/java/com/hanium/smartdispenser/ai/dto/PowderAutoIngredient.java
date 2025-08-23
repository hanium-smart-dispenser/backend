package com.hanium.smartdispenser.ai.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.hanium.smartdispenser.ai.AiUtils.roundOrZero;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PowderAutoIngredient(
        String type,
        String ingredient,
        String pumpId,
        Double targetG,
        Double perPumpG,
        Integer pumpCounts,
        Double estimatedDeliveredG,
        Double overshootG

) implements AutoIngredient {

    @Override
    public int computeGrams() {
        if (estimatedDeliveredG != null) return roundOrZero(estimatedDeliveredG);
        if (perPumpG != null && pumpCounts != null) return roundOrZero(perPumpG * pumpCounts);
        return roundOrZero(targetG);
    }
}
