package com.hanium.smartdispenser.dispenser.service;

import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.dto.DispenserDto;
import com.hanium.smartdispenser.dispenser.repository.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.exception.DispenserNotFoundException;
import com.hanium.smartdispenser.dispenser.exception.UnauthorizedDispenserAccessException;
import com.hanium.smartdispenser.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<DispenserDto> findAllByUserId(Long userId, Pageable pageable) {
        Page<Dispenser> dispensers = dispenserRepository.findAllByUserIdWithPaging(userId, pageable);
        return dispensers.map(DispenserDto::of);
    }

    public void validateUserAccess(Long userId, Long dispenserId) {
        Dispenser dispenser = findById(dispenserId);
        if (!dispenser.getUser().getId().equals(userId)) {
            throw new UnauthorizedDispenserAccessException(userId, dispenserId);
        }
    }

    public void createDispenser(String name, User user) {
        dispenserRepository.save(Dispenser.of(name, DispenserStatus.CONNECTED, user));
    }

    public boolean canDispense(Long dispenserId) {
        return findById(dispenserId).getStatus() == DispenserStatus.CONNECTED;
    }



}
