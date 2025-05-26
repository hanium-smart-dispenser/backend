package com.hanium.smartdispenser.dispenser.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
import lombok.Getter;

@Getter
public class SauceListDto {

    private final int slot;
    private final Long ingredientId;
    private final boolean isLow;

    public static SauceListDto of(DispenserSauce dispenserSauce) {
        return new SauceListDto(dispenserSauce.getSlot(), dispenserSauce.getIngredient().getId(), dispenserSauce.isLow());
    }

    @JsonCreator
    public SauceListDto(@JsonProperty("slot") int slot,
                        @JsonProperty("ingredientId") Long ingredientId,
                        @JsonProperty("isLow") boolean isLow) {
        this.slot = slot;
        this.ingredientId = ingredientId;
        this.isLow = isLow;
    }
}

/*

mosquitto_pub -h localhost -p 1883 -t dispenser/1/status -m '{"dispenserId": 2, "sources": [{"slot": 1, "ingredientId": 1, "isLow": false} ,{"slot": 2, "ingredientId": 2, "isLow": false} ]}'
 */
