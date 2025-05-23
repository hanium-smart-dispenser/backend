package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserSource;
import com.hanium.smartdispenser.dispenser.dto.DispenserStatusDto;
import com.hanium.smartdispenser.dispenser.dto.SourceListDto;
import com.hanium.smartdispenser.dispenser.repository.DispenserSourceRepository;
import com.hanium.smartdispenser.ingredient.IngredientService;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DispenserSourceService {

    private final DispenserSourceRepository dispenserSourceRepository;
    private final DispenserService dispenserService;
    private final IngredientService ingredientService;


    @Transactional
    public void addSourceToDispenser(DispenserStatusDto requestDto) {
        Dispenser dispenser = dispenserService.findById(requestDto.getDispenserId());
        List<SourceListDto> sourceList = requestDto.getSources();
        for (SourceListDto sourceListDto : sourceList) {
            Ingredient ingredient = ingredientService.findById(sourceListDto.getIngredientId());
            DispenserSource source = DispenserSource.of(sourceListDto.getSlot(), ingredient);
            dispenser.addSource(source);
        }
    }

    public DispenserStatusDto getDispenserStatus(Long dispenserId) {
        Dispenser dispenser = dispenserService.findById(dispenserId);
        List<DispenserSource> sourceList = dispenserSourceRepository.findAllByDispenser(dispenserId);
        return DispenserStatusDto.of(dispenser, sourceList);
    }
}
