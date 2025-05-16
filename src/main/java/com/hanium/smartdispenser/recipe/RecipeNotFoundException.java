package com.hanium.smartdispenser.recipe;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class RecipeNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "레시피(%s)를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "RECIPE_NOT_FOUND";
    public RecipeNotFoundException(Long recipeId) {
        super(String.format(DEFAULT_MESSAGE, recipeId), ERROR_CODE);
    }

    public RecipeNotFoundException(Throwable cause, Long recipeId) {
        super(String.format(DEFAULT_MESSAGE, recipeId), cause, ERROR_CODE);
    }
}
