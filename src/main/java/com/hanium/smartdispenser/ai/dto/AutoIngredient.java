package com.hanium.smartdispenser.ai.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(
        use = Id.NAME,
        include = As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LiquidAutoIngredient.class, name = "liquid"),
        @JsonSubTypes.Type(value = PowderAutoIngredient.class, name = "solid")
})
public sealed interface AutoIngredient permits LiquidAutoIngredient, PowderAutoIngredient {
    String type();

    String ingredient();

    String pumpId();

    Double targetG();
}
