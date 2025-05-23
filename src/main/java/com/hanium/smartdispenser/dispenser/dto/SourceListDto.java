package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.dispenser.domain.DispenserSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SourceListDto {

    private final int slot;
    private final Long ingredientId;

    public static SourceListDto of(DispenserSource dispenserSource) {
        return new SourceListDto(dispenserSource.getSlot(), dispenserSource.getIngredient().getId());
    }

}
