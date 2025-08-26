package com.hanium.smartdispenser.recipe;

import com.hanium.smartdispenser.recipe.domain.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Recipe findById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
    }

    public Recipe findByIdWithIngredients(Long recipeId) {
        return recipeRepository.findByIdWithIngredients(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
    }
}
