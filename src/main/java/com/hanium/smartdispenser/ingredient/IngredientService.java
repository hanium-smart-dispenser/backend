package com.hanium.smartdispenser.ingredient;

import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient findById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(() -> new IngredientNotFoundException(ingredientId));
    }

    public Ingredient findOrCreateIngredient(String name, IngredientType type) {
        return ingredientRepository.findByName(name).orElseGet(() -> ingredientRepository.save(Ingredient.of(name, type)));
    }
}
