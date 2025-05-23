package com.hanium.smartdispenser.dispenser.dto;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class DispenserStatusDto {

    private final Long dispenserId;
    private final List<SourceListDto> sources;

    public static DispenserStatusDto of(Dispenser dispenser, List<DispenserSource> sourceList) {
        List<SourceListDto> sourceListDtos = sourceList.stream().map((ds) -> new SourceListDto(
                ds.getSlot(), ds.getIngredient().getId())).toList();
        return new DispenserStatusDto(dispenser.getId(), sourceListDtos);
    }
}
