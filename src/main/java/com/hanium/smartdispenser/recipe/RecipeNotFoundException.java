package com.hanium.smartdispenser.recipe;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class RecipeNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "레시피를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "RECIPE_NOT_FOUND";
    public RecipeNotFoundException(Long recipeId) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("recipeId", recipeId);
    }
}
