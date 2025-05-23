package com.hanium.smartdispenser.dispenser.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
public class DispenserStatusDto {

    private final Long dispenserId;
    private final List<SourceListDto> sources;

    public static DispenserStatusDto of(Dispenser dispenser, List<DispenserSource> sourceList) {
        List<SourceListDto> sourceListDtos = sourceList.stream().map((ds) -> new SourceListDto(
                ds.getSlot(), ds.getIngredient().getId(), ds.isLow())).toList();
        return new DispenserStatusDto(dispenser.getId(), sourceListDtos);
    }

    @JsonCreator
    public DispenserStatusDto(@JsonProperty("dispenserId") Long dispenserId,
                              @JsonProperty("sources") List<SourceListDto> sources) {
        this.dispenserId = dispenserId;
        this.sources = sources;
    }
}
