package com.hanium.smartdispenser.ai.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;
import static com.hanium.smartdispenser.ai.AiUtils.roundOrZero;

@JsonNaming(SnakeCaseStrategy.class)
public record LiquidAutoIngredient(
        String type,
        String ingredient,
        String pumpId,
        Double targetG,
        Double pumpTimeSec
) implements AutoIngredient {

    @Override
    public int computeGrams() {
        return roundOrZero(targetG);
    }
}
