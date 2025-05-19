package com.hanium.smartdispenser.history.dto;

import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class HistoryResponseDto {

    private final Long historyId;
    private final HistoryStatus status;
    private final LocalDateTime requestAt;
    private final LocalDateTime completedAt;
    private final Long userId;
    private final Long dispenserId;
    private final Long recipeId;

    public static HistoryResponseDto of(History history) {
        return new HistoryResponseDto(history.getId(), history.getStatus(),
                history.getRequestedAt(), history.getCompletedAt(), history.getDispenser().getId(),
                history.getRecipe().getId(), history.getUser().getId());
    }
}
