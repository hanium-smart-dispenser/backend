package com.hanium.smartdispenser.recipe;

import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.recipe.dto.IngredientWithAmountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeParsingFacade {

    private final RecipeService recipeService;

    public List<IngredientWithAmountDto> getIngredientsList(Long recipeId) {
        Recipe recipe = recipeService.findById(recipeId);

        return recipe.getRecipeIngredientList().stream()
                .map(ri -> new IngredientWithAmountDto(ri.getIngredient().getId(), ri.getAmount(), ri.getIngredient().getType()))
                .toList();
    }

}
