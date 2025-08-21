package com.hanium.smartdispenser.ai.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

@JsonNaming(SnakeCaseStrategy.class)
public record LiquidAutoIngredient(
        String type,
        String ingredient,
        String pumpId,
        Double targetG,
        Double pumpTimeSec
) implements AutoIngredient {

}
