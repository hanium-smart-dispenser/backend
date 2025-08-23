package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.dto.DispenserStatusDto;
import com.hanium.smartdispenser.dispenser.dto.SauceDto;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotReadyException;
import com.hanium.smartdispenser.dispenser.repository.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotFoundException;
import com.hanium.smartdispenser.dispenser.exception.UnauthorizedDispenserAccessException;
import com.hanium.smartdispenser.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DispenserService {

    private final DispenserRepository dispenserRepository;
    private final UserService userService;
    public Dispenser findById(Long dispenserId) {
        return dispenserRepository.findById(dispenserId).orElseThrow(() -> DispenserNotFoundException.byDispenserId(dispenserId));
    }

    public Dispenser findByUuid(String uuid) {
        return dispenserRepository.findByUuid(uuid).orElseThrow(() -> DispenserNotFoundException.byUuid(uuid));
    }

    public Dispenser findByUuidWithSauces(String uuid) {
        return dispenserRepository.findByUuidWithSauces(uuid).orElseThrow(() -> DispenserNotFoundException.byUuid(uuid));
    }

    public Dispenser findByUserId(Long userId) {
        return dispenserRepository.findByUser_Id(userId).orElseThrow(() -> DispenserNotFoundException.byUserId(userId));
    }

    public Dispenser findByUserIdWithSauces(Long userId) {
        return dispenserRepository.findByUser_Id(userId).orElseThrow(() -> DispenserNotFoundException.byUserId(userId));
    }

    public void validateUserAccess(Long userId, Long dispenserId) {
        Dispenser dispenser = findById(dispenserId);
        if (!dispenser.getUser().getId().equals(userId)) {
            throw new UnauthorizedDispenserAccessException(userId, dispenserId);
        }
    }

    public Long createDispenser(Dispenser dispenser) {
        return dispenserRepository.save(dispenser).getId();
    }

    public void validateDispenserStatus(Long dispenserId) {
        DispenserStatus status = findById(dispenserId).getStatus();
        if (status != DispenserStatus.READY) {
            throw new DispenserNotReadyException(status);
        }
    }

    public void updateDispenserStatus(Long dispenserId, DispenserStatus status) {
        Dispenser dispenser = findById(dispenserId);
        dispenser.updateStatus(status);
    }

    public void updateDispenserSauces(DispenserStatusDto dto) {
        Dispenser dispenser = findByUuidWithSauces(dto.uuid());
        List<SauceDto> sauces = dto.sauces();

        for (SauceDto sauce : sauces) {
            dispenser.updateSauces(sauce.slot(), sauce.isLow());
        }
    }


    public void assignUser(Long userId, Long dispenserId) {
        Dispenser dispenser = findById(dispenserId);
        dispenser.assignUser(userService.findById(userId));
    }

}
