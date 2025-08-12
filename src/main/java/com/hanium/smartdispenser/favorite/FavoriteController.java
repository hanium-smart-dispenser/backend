package com.hanium.smartdispenser.favorite;


import com.hanium.smartdispenser.auth.UserPrincipal;
import com.hanium.smartdispenser.recipe.RecipeService;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final RecipeService recipeService;


    @PostMapping
    public ResponseEntity<Void> addFavoriteRecipe(@AuthenticationPrincipal UserPrincipal user, @RequestBody FavoriteRequestDto requestDto) {

        Recipe recipe = recipeService.findById(requestDto.getRecipeId());
        User findUser = userService.findById(user.getUserId());

        favoriteService.addFavoriteRecipe(findUser, recipe);
        return ResponseEntity.ok().build();
    }



}
