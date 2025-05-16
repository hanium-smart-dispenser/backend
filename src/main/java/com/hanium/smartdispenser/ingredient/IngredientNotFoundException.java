package com.hanium.smartdispenser.ingredient;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class IngredientNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "재료(%s)를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "INGREDIENT_NOT_FOUND";
    public IngredientNotFoundException(Long ingredientId) {
        super(String.format(DEFAULT_MESSAGE, ingredientId), ERROR_CODE);
    }

    public IngredientNotFoundException(Throwable cause, Long ingredientId) {
        super(String.format(DEFAULT_MESSAGE, ingredientId), cause, ERROR_CODE);
    }
}
