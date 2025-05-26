package com.hanium.smartdispenser.dispenser.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
import lombok.Getter;

import java.util.List;


@Getter
public class DispenserStatusDto {

    private final Long dispenserId;
    private final List<SauceListDto> sauces;

    public static DispenserStatusDto of(Dispenser dispenser, List<DispenserSauce> sauceList) {
        List<SauceListDto> sauceListDtos = sauceList.stream().map((ds) -> new SauceListDto(
                ds.getSlot(), ds.getIngredient().getId(), ds.isLow())).toList();
        return new DispenserStatusDto(dispenser.getId(), sauceListDtos);
    }

    @JsonCreator
    public DispenserStatusDto(@JsonProperty("dispenserId") Long dispenserId,
                              @JsonProperty("sauces") List<SauceListDto> sauces) {
        this.dispenserId = dispenserId;
        this.sauces = sauces;
    }
}
