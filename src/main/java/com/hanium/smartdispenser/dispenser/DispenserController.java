package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.auth.UserPrincipal;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandRequestDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandResponseDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserDto;
import com.hanium.smartdispenser.dispenser.service.DispenserCommandFacade;
import com.hanium.smartdispenser.dispenser.service.DispenserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/dispensers")
public class DispenserController {

    private final DispenserCommandFacade dispenserCommandFacade;
    private final DispenserService dispenserService;

    @GetMapping
    public Page<DispenserDto> sendDispenserList(@AuthenticationPrincipal UserPrincipal user, Pageable pageable) {
        return dispenserService.findAllByUserId(user.getUserId(), pageable);
    }

    @PostMapping("/{dispenserId}/command")
    public ResponseEntity<DispenserCommandResponseDto> sendCommand(
            @PathVariable Long dispenserId,
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody DispenserCommandRequestDto requestDto) {
        DispenserCommandResponseDto responseDto = dispenserCommandFacade.sendCommand(
                dispenserId, user.getUserId(), requestDto.getRecipeId());
        return ResponseEntity.ok(responseDto);
    }
}
