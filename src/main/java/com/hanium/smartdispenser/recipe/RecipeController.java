package com.hanium.smartdispenser.recipe;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Void> createRecipe(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody @Valid RecipeCreateRequestDto requestDto) {
        recipeService.createRecipe(Long.valueOf(user.getUsername()), requestDto.getName(),
                requestDto.getIngredients());
        return ResponseEntity.ok().build();
    }
}
