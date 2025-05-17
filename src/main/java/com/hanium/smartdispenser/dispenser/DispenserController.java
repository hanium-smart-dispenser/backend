package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.dispenser.dto.DispenserCommandRequestDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandResponseDto;
import com.hanium.smartdispenser.dispenser.service.DispenserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/dispensers")
public class DispenserController {

    private final DispenserService dispenserService;

    @PostMapping("/{dispenserId}/command")
    public ResponseEntity<DispenserCommandResponseDto> sendCommand(
            @PathVariable Long dispenserId,
            @AuthenticationPrincipal UserDetails user,
            @RequestBody DispenserCommandRequestDto requestDto) {
        DispenserCommandResponseDto responseDto = dispenserService.sendCommand(
                dispenserId, Long.valueOf(user.getUsername()), requestDto.getRecipeId());
        return ResponseEntity.ok(responseDto);
    }
}
