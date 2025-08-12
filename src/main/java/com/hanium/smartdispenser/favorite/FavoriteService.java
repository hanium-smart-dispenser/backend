package com.hanium.smartdispenser.favorite;

import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public Favorite addFavoriteRecipe(User user, Recipe recipe) {
        return favoriteRepository.save(Favorite.of(user, recipe));
    }


}
