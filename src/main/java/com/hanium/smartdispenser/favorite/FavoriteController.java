package com.hanium.smartdispenser.favorite;


import com.hanium.smartdispenser.auth.UserPrincipal;
import com.hanium.smartdispenser.recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/me")
    public ResponseEntity<Void> addFavoriteRecipe(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody FavoriteDto dto) {
        favoriteService.addFavoriteRecipe(user.getUserId(), dto.getRecipeId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public Page<RecipeDto> getFavoriteRecipe(
            @AuthenticationPrincipal UserPrincipal user,
            Pageable pageable) {
        return favoriteService.getFavoriteRecipes(user.getUserId(), pageable).map(f -> new RecipeDto(f.getRecipe()));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteFavoriteRecipe(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody FavoriteDto dto
    ) {
        favoriteService.deleteFavoriteRecipe(user.getUserId(), dto.getRecipeId());
        return ResponseEntity.ok().build();
    }
}
