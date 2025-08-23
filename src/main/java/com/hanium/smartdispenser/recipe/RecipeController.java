package com.hanium.smartdispenser.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.hanium.smartdispenser.auth.UserPrincipal;
import com.hanium.smartdispenser.ingredient.IngredientSnapshotService;
import com.hanium.smartdispenser.ingredient.domain.IngredientSnapshot;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.recipe.dto.RecipeAiCreateDto;
import com.hanium.smartdispenser.recipe.dto.RecipeDto;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final UserService userService;
    private final RecipeService recipeService;
    private final RecipeAssembler recipeAssembler;
    private final IngredientSnapshotService ingredientSnapshotService;
    @PostMapping("/ai")
    public ResponseEntity<Void> createRecipeToAi(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody RecipeAiCreateDto dto
    ) {
        recipeAssembler.create(userService.findById(user.getUserId()), dto.prompt());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.findByIdWithIngredients(id);
        return ResponseEntity.ok(new RecipeDto(recipe));
    }

    @GetMapping("/{id}/manual")
    public JsonNode getIngredientSnapshot(@PathVariable Long id) {
        IngredientSnapshot ingredientSnapshot = ingredientSnapshotService.findByRecipeId(id);
        return ingredientSnapshot.getPayload();
    }
}
