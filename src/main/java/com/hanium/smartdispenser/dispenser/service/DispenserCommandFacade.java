package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.common.JsonMapper;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandPayLoadDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandResponseDto;
import com.hanium.smartdispenser.dispenser.exception.DispenserCommandSendFailedException;
import com.hanium.smartdispenser.history.HistoryService;
import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import com.hanium.smartdispenser.recipe.RecipeParsingFacade;
import com.hanium.smartdispenser.recipe.RecipeService;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.recipe.dto.IngredientWithAmountDto;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DispenserCommandFacade {

    private final HistoryService historyService;
    private final RecipeParsingFacade recipeParsingFacade;
    //나중에 수정
    private final RecipeService recipeService;
    private final DispenserService dispenserService;
    private final UserService userService;
    private final MqttService mqttService;
    private final JsonMapper<DispenserCommandPayLoadDto> mapper;

    @Transactional
    public DispenserCommandResponseDto sendCommand(Long dispenserId, Long userId, Long recipeId) {
        LocalDateTime start = LocalDateTime.now();
        Dispenser dispenser = dispenserService.findById(dispenserId);
        User user = userService.findById(userId);
        Recipe recipe = recipeService.findById(recipeId);

        List<IngredientWithAmountDto> ingredients = recipeParsingFacade.getIngredientsList(recipeId);
        dispenserService.validateUserAccess(userId, dispenserId);
        dispenserService.validateDispenserStatus(dispenserId);

        String commandId = UUID.randomUUID().toString();
        History history = History.of(user, dispenser, recipe, LocalDateTime.now());
        historyService.createHistory(history);

        try {
            DispenserCommandPayLoadDto payLoadDto = new DispenserCommandPayLoadDto(commandId, userId, recipeId, ingredients, LocalDateTime.now());
            dispenser.updateStatus(DispenserStatus.BUSY);
            String payload = mapper.toJson(payLoadDto);
            mqttService.sendCommand(dispenserId, payload);
            history.updateStatus(HistoryStatus.PROCESSING);
        } catch (DispenserCommandSendFailedException e) {
            history.updateStatus(HistoryStatus.FAIL);
            throw e;
        }

        history.markSuccess();
        return new DispenserCommandResponseDto(commandId, dispenserId, userId, recipeId, history.getStatus(), start, LocalDateTime.now());
    }


    public void createDispenser(Long userId) {
        User user = userService.findById(userId);
        dispenserService.createDispenser(Dispenser.of(DispenserStatus.CONNECTED, user));
    }
}
