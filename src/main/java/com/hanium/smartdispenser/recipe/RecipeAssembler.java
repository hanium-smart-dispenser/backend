package com.hanium.smartdispenser.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.smartdispenser.ai.AiService;
import com.hanium.smartdispenser.ai.dto.AiResponse;
import com.hanium.smartdispenser.ai.dto.AutoIngredient;
import com.hanium.smartdispenser.ingredient.IngredientService;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.ingredient.domain.IngredientSnapshot;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.ingredient.repository.IngredientSnapshotRepository;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeAssembler {

    private final AiService aiService;
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final IngredientSnapshotRepository ingredientSnapshotRepository;
    private final ObjectMapper objectMapper;

    public JsonNode create(User user, String prompt) {
        AiResponse dto = aiService.getRecipe(prompt);
        Recipe recipe = Recipe.of(dto.name(), user);

        recipeRepository.save(recipe);

        if (dto.auto_ingredients() != null) {
            for (AutoIngredient autoIng : dto.auto_ingredients()) {
                String name = autoIng.ingredient();
                if (name == null) {
                    continue;
                }

                IngredientType type = mapType(autoIng.type());
                Ingredient ing = ingredientService.findOrCreateIngredient(name, type);
                int grams = autoIng.computeGrams();
                recipe.addIngredient(ing, grams);
            }
        }

        if (dto.manual_ingredients() != null && dto.manual_ingredients().isEmpty()) {
            JsonNode jsonNode = objectMapper.valueToTree(dto.manual_ingredients());
            ingredientSnapshotRepository.save(IngredientSnapshot.of(recipe, jsonNode));
        }

        return objectMapper.valueToTree(dto);
    }

    /** auto g 계산: liquid는 target_g, powder는 estimated_delivered_g → per_pump_g * pump_counts → target_g 순서 */

    private int round(double d) {
        return (int) Math.round(d);
    }

    private IngredientType mapType(String type) {
        if( type == null) return IngredientType.POWDER;
        return switch (type.toLowerCase()) {
            case "liquid" -> IngredientType.LIQUID;
            case "powder" -> IngredientType.POWDER;
            default -> IngredientType.POWDER;
        };
    }
}
