package com.hanium.smartdispenser.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.hanium.smartdispenser.ai.AiService;
import com.hanium.smartdispenser.ingredient.IngredientService;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.ingredient.domain.IngredientSnapshot;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.ingredient.repository.IngredientSnapshotRepository;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Transactional
public class RecipeAssembler {

    private final AiService aiService;
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final IngredientSnapshotRepository ingredientSnapshotRepository;

    public JsonNode create(User user, String prompt) {
        JsonNode payload = aiService.getRecipeRaw(prompt);

        JsonNode autoArr = payload.path("auto_ingredients");
        Recipe recipe = Recipe.of(payload.path("name").asText(), user);

        recipeRepository.save(recipe);

        if (autoArr.isArray()) {
            for (Iterator<JsonNode> it = autoArr.elements(); it.hasNext(); ) {
                JsonNode n = it.next();

                String name = n.path("ingredient").asText(null);
                if(name == null) continue;

                IngredientType type = mapType(n.path("type").asText(null));
                Ingredient ingredient = ingredientService.findOrCreateIngredient(name, type);

                int grams = computeAutoGrams(n);
                recipe.addIngredient(ingredient, grams);
            }
        }

        JsonNode manualArr = payload.path("manual_ingredients"); // 배열 전체
        if (manualArr != null && !manualArr.isMissingNode()) {
            ingredientSnapshotRepository.save(IngredientSnapshot.of(recipe, manualArr));
        }

        return payload;
    }

    /** auto g 계산: liquid는 target_g, powder는 estimated_delivered_g → per_pump_g * pump_counts → target_g 순서 */
    private int computeAutoGrams(JsonNode n) {
        // liquid: target_g 우선
        if ("liquid".equalsIgnoreCase(n.path("type").asText(null))) {
            return round(n.path("target_g").asDouble(0));
        }
        // solid: estimated_delivered_g > per_pump_g * pump_counts > target_g
        if (n.hasNonNull("estimated_delivered_g")) {
            return round(n.get("estimated_delivered_g").asDouble());
        }
        if (n.hasNonNull("per_pump_g") && n.hasNonNull("pump_counts")) {
            double g = n.get("per_pump_g").asDouble(0) * n.get("pump_counts").asInt(0);
            return round(g);
        }
        return round(n.path("target_g").asDouble(0));
    }

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

    private int safeRound(Double g) {
        if (g == null) return 0;
        return (int) round(g);
    }

}
