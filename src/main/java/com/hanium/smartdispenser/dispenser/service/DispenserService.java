package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.dispenser.DispenserCommandResult;
import com.hanium.smartdispenser.dispenser.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandRequestDto;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotFoundException;
import com.hanium.smartdispenser.dispenser.exception.UnauthorizedDispenserAccessException;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DispenserService {

    private final DispenserRepository dispenserRepository;
    private final MqttService mqttService;
    private final UserService userService;

    public DispenserCommandResult sendCommand(Long dispenserId, Long userId, DispenserCommandRequestDto requestDto) {

        LocalDateTime now = LocalDateTime.now();
        User user = userService.findById(userId);
        Dispenser dispenser = findById(dispenserId);

        if (!dispenser.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedDispenserAccessException(user.getId(), dispenserId);
        }

        String commandId = UUID.randomUUID().toString();

        //레시피 조회
        //dto 내용 parsing -> recipe
        mqttService.sendCommand(dispenserId, "aa");
        //history 추가
        return new DispenserCommandResult(commandId, HistoryStatus.SUCCESS, requestDto.getRequestedAt());
    }
    public Dispenser findById(Long id) {
        return dispenserRepository.findById(id).orElseThrow(DispenserNotFoundException::new);
    }
}
