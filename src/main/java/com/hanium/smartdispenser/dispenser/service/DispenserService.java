package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotReadyException;
import com.hanium.smartdispenser.dispenser.repository.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotFoundException;
import com.hanium.smartdispenser.dispenser.exception.UnauthorizedDispenserAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DispenserService {

    private final DispenserRepository dispenserRepository;
    public Dispenser findById(Long dispenserId) {
        return dispenserRepository.findById(dispenserId).orElseThrow(() -> new DispenserNotFoundException(dispenserId));
    }

    public Dispenser findByUser(Long userId) {
        Dispenser dispenser = dispenserRepository.findByUser(userId);
        validateUserAccess(userId, dispenser.getId());
        return dispenser;
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

    @Transactional
    public void updateDispenserStatus(Long dispenserId, DispenserStatus status) {
        Dispenser dispenser = findById(dispenserId);
        dispenser.updateStatus(status);
    }

}
