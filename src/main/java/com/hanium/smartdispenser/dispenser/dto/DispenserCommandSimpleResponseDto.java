package com.hanium.smartdispenser.dispenser.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import lombok.Getter;

@Getter
public class DispenserCommandSimpleResponseDto {

    private final String commandId;
    private final Long dispenserId;
    private final Long historyId;
    private final HistoryStatus status;

    @JsonCreator
    public DispenserCommandSimpleResponseDto(@JsonProperty String commandId,
                                             @JsonProperty Long dispenserId,
                                             @JsonProperty Long historyId,
                                             @JsonProperty HistoryStatus status) {

        this.commandId = commandId;
        this.dispenserId = dispenserId;
        this.historyId = historyId;
        this.status = status;
    }
}
