package com.hanium.smartdispenser.dispenser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandPayLoadDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandResponseDto;
import com.hanium.smartdispenser.dispenser.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.exception.DispenserCommandSendFailedException;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotFoundException;
import com.hanium.smartdispenser.dispenser.exception.UnauthorizedDispenserAccessException;
import com.hanium.smartdispenser.history.HistoryService;
import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
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
@Transactional(readOnly = true)
public class DispenserService {

    private final DispenserRepository dispenserRepository;
    private final MqttService mqttService;
    private final UserService userService;
    private final RecipeService recipeService;
    private final HistoryService historyService;
    private final ObjectMapper objectMapper;


    // 다른 Service에서 던진 예외 감싸기 추가해야댐.
    @Transactional
    public DispenserCommandResponseDto sendCommand(Long dispenserId, Long userId, Long recipeId) {
        LocalDateTime start = LocalDateTime.now();
        User user = userService.findById(userId);
        Dispenser dispenser = findById(dispenserId);
        Recipe recipe = recipeService.findById(recipeId);

        //List<RecipeIngredient> >> List<IngredientWithAmountDto>
        List<IngredientWithAmountDto> ingredients = recipe.getRecipeIngredientList().stream()
                .map(ri -> new IngredientWithAmountDto(ri.getIngredient().getId(), ri.getAmount(), ri.getIngredient().getType()))
                .toList();

        if (dispenser.getStatus() != DispenserStatus.READY) {
            throw new DispenserCommandSendFailedException();
        }

        //디스펜서 소유자 확인
        if (!dispenser.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedDispenserAccessException(user.getId(), dispenserId);
        }

        String commandId = UUID.randomUUID().toString();

        History history = History.of(user, dispenser, recipe, LocalDateTime.now());
        historyService.createHistory(history);

        try {
            DispenserCommandPayLoadDto payLoadDto = new DispenserCommandPayLoadDto(commandId, userId, recipeId, ingredients, LocalDateTime.now());
            dispenser.updateStatus(DispenserStatus.BUSY);
            mqttService.sendCommand(dispenserId, dtoToJson(payLoadDto));
            history.updateStatus(HistoryStatus.PROCESSING);
        } catch (DispenserCommandSendFailedException e) {
            history.updateStatus(HistoryStatus.FAIL);
            throw e;
        }

        history.markSuccess();
        return new DispenserCommandResponseDto(commandId, dispenserId, userId, recipeId, history.getStatus(), start, LocalDateTime.now());
    }
    private String dtoToJson(DispenserCommandPayLoadDto payLoadDto) {
        try {
            return objectMapper.writeValueAsString(payLoadDto);
        } catch (JsonProcessingException e) {
            throw new DispenserCommandSendFailedException(e);
        }
    }

    public Dispenser findById(Long id) {
        return dispenserRepository.findById(id).orElseThrow(DispenserNotFoundException::new);
    }
}
