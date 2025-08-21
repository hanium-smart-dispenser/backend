package com.hanium.smartdispenser.ingredient;

import com.hanium.smartdispenser.ingredient.domain.IngredientSnapshot;
import com.hanium.smartdispenser.ingredient.repository.IngredientSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientSnapshotService {

    private final IngredientSnapshotRepository ingredientSnapshotRepository;

    public IngredientSnapshot findByRecipeId(Long recipeId) {
        return ingredientSnapshotRepository.findByRecipe_Id(recipeId).orElseThrow(() -> new IllegalStateException("이 레시피의 메뉴얼이 없습니다."));
    }


}
