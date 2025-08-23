package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotFoundException;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotReadyException;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.respository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DispenserServiceTest {

    @Autowired
    DispenserService dispenserService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("디스펜서가 준비되지 않으면 예외를 반환한다.")
    void validateDispenserStatus_throwException_whenDispenserNotReady() {
        User user = User.of("test1", "1234", "test@test.com");
        userRepository.save(user);
        Dispenser dispenser = Dispenser.of(DispenserStatus.CONNECTED, user, UUID.randomUUID().toString());
        dispenserService.createDispenser(dispenser);

        assertThatThrownBy(() ->
                dispenserService.validateDispenserStatus(dispenser.getId()))
                .isInstanceOf(DispenserNotReadyException.class);


        dispenser.updateStatus(DispenserStatus.BUSY);
        assertThatThrownBy(() ->
                dispenserService.validateDispenserStatus(dispenser.getId()))
                .isInstanceOf(DispenserNotReadyException.class);

        dispenser.updateStatus(DispenserStatus.DISCONNECTED);
        assertThatThrownBy(() ->
                dispenserService.validateDispenserStatus(dispenser.getId()))
                .isInstanceOf(DispenserNotReadyException.class);

        dispenser.updateStatus(DispenserStatus.ERROR);
        assertThatThrownBy(() ->
                dispenserService.validateDispenserStatus(dispenser.getId()))
                .isInstanceOf(DispenserNotReadyException.class);
    }

    @Test
    @DisplayName("존재하지 않는 Dispenser 조회 시 예외를 반환한다.")
    void findById_throwException_whenDispenserDoesNotExist() {
        assertThatThrownBy(() -> dispenserService.findById(123L))
                .isInstanceOf(DispenserNotFoundException.class);
    }


    @Test
    @DisplayName("Dispenser를 생성하고 Id로 조회 할 수 있다.")
    void findByEmail_returnUser_whenUserExist() {
        User user = User.of("test1", "1234", "test@test.com");
        userRepository.save(user);

        Dispenser dispenser = Dispenser.of(DispenserStatus.CONNECTED, user, UUID.randomUUID().toString());
        Long dispenserId = dispenserService.createDispenser(dispenser);

        Dispenser findDispenser = dispenserService.findById(dispenserId);

        assertThat(findDispenser).isEqualTo(dispenser);
    }
}