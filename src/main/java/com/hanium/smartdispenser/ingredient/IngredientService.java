package com.hanium.smartdispenser.ingredient;

import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient findById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(() -> new IngredientNotFoundException(ingredientId));
    }
}
