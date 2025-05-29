package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
import com.hanium.smartdispenser.dispenser.dto.DispenserStatusDto;
import com.hanium.smartdispenser.dispenser.dto.SauceListDto;
import com.hanium.smartdispenser.dispenser.repository.DispenserSauceRepository;
import com.hanium.smartdispenser.ingredient.IngredientService;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DispenserSauceService {

    private final DispenserSauceRepository dispenserSauceRepository;
    private final DispenserService dispenserService;
    private final IngredientService ingredientService;


    public void updateStatus(DispenserStatusDto statusDto) {
        Dispenser dispenser = dispenserService.findById(statusDto.getDispenserId());
        List<SauceListDto> sauceList = statusDto.getSauces();
        for (SauceListDto sauceListDto : sauceList) {
            Ingredient ingredient = ingredientService.findById(sauceListDto.getIngredientId());
            DispenserSauce sauce = DispenserSauce.of(sauceListDto.getSlot(), ingredient);
            dispenser.addSauce(sauce);
        }
    }

    public DispenserStatusDto getDispenserStatus(Long dispenserId) {
        Dispenser dispenser = dispenserService.findById(dispenserId);
        log.info("쿼리 확인 START");
        List<DispenserSauce> sauceList = dispenserSauceRepository.findAllByDispenser(dispenserId);
        log.info("쿼리 확인 END");
        return DispenserStatusDto.of(dispenser, sauceList);
    }
}
