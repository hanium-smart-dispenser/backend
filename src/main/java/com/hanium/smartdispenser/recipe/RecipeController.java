package com.hanium.smartdispenser.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.hanium.smartdispenser.ai.AiService;
import com.hanium.smartdispenser.ai.dto.AiResponse;
import com.hanium.smartdispenser.auth.UserPrincipal;
import com.hanium.smartdispenser.ingredient.IngredientSnapshotService;
import com.hanium.smartdispenser.ingredient.domain.IngredientSnapshot;
import com.hanium.smartdispenser.recipe.dto.RecipeAiResponse;
import com.hanium.smartdispenser.recipe.dto.RecipeCreateRequestDto;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.service.UserService;
import jakarta.validation.Valid;
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

    @PostMapping("/me")
    public ResponseEntity<Void> createRecipe(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody @Valid RecipeCreateRequestDto requestDto) {
        recipeService.createRecipe(user.getUserId(), requestDto.getRecipeName(),
                requestDto.getIngredients());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ai")
    public ResponseEntity<Void> createRecipeToAi(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody String prompt
    ) {
        recipeAssembler.create(userService.findById(user.getUserId()), prompt);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/manual")
    public JsonNode getIngredientSnapshot(@PathVariable Long recipeId) {
        IngredientSnapshot ingredientSnapshot = ingredientSnapshotService.findByRecipeId(recipeId);
        return ingredientSnapshot.getPayload();
    }
}
