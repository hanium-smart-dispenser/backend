package com.hanium.smartdispenser.history.dto;

import com.hanium.smartdispenser.history.domain.HistoryStatus;
import com.hanium.smartdispenser.ingredient.IngredientName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class HistoryDto {
    private final Long historyId;
    private final HistoryStatus status;
    private final LocalDateTime requestedAt;
    private final LocalDateTime completedAt;
    private final Long userId;
    private final Long dispenserId;
    private final Long recipeId;
    private final String recipeName;
    private final List<IngredientName> ingredientsNames;
    private final boolean isFavorite;

}
