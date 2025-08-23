package com.hanium.smartdispenser.recipe;

import com.hanium.smartdispenser.ingredient.IngredientService;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.recipe.dto.IngredientWithAmountDto;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
