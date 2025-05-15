package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispenserService {

    private final DispenserRepository dispenserRepository;
    private final MqttService mqttService;
    private final UserService userService;

    public DispenserCommandResult sendCommand(Long dispenserId, Long userId, DispenserCommandRequestDto requestDto) {
        User user = userService.findById(userId);
        Dispenser dispenser = findById(dispenserId);

        if (!dispenser.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedDispenserAccessException(user.getId(), dispenserId);
        }
        //레시피 조회
        //dto 내용 parsing -> recipe
        mqttService.sendCommand(dispenserId, "aa");
        //history 추가
        return new DispenserCommandResult("test", HistoryStatus.SUCCESS, requestDto.getRequestedAt());
    }

    public Dispenser findById(Long id) {
        return dispenserRepository.findById(id).orElseThrow(DispenserNotFoundException::new);
    }
}
