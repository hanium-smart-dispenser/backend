package com.hanium.smartdispenser.favorite;

import com.hanium.smartdispenser.recipe.RecipeService;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final UserService userService;
    private final RecipeService recipeService;
    private final FavoriteRepository favoriteRepository;

    public Favorite addFavoriteRecipe(Long userId, Long recipeId) {
        User user = userService.findById(userId);
        Recipe recipe = recipeService.findById(recipeId);
        return favoriteRepository.save(Favorite.of(user, recipe));
    }

    public Page<Favorite> getFavoriteRecipes(Long userId, Pageable pageable) {
        User user = userService.findById(userId);
        return favoriteRepository.findByUser_Id(user.getId(), pageable);
    }


}
