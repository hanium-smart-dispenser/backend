package com.hanium.smartdispenser.favorite;

import com.hanium.smartdispenser.common.exception.BusinessException;

public class FavoriteNotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "즐겨찾기를 찾을 수 없습니다.";
    public static final String ERROR_CODE = "FAVORITE_NOT_FOUND";
    public FavoriteNotFoundException(Long userId, Long recipeId) {
        super(DEFAULT_MESSAGE, ERROR_CODE);
        put("userId", userId);
        put("recipeId", recipeId);
    }
}
