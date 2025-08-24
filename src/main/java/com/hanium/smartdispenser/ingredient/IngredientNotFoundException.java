package com.hanium.smartdispenser.ingredient;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class IngredientNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "재료를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "INGREDIENT_NOT_FOUND";
    public IngredientNotFoundException(Long ingredientId) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("ingredientId", ingredientId);
    }
}
