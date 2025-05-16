package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.dispenser.dto.DispenserCommandRequestDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandResponseDto;
import com.hanium.smartdispenser.dispenser.service.DispenserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody DispenserCommandRequestDto requestDto) {
        dispenserService.sendCommand(dispenserId, Long.valueOf(user.getName()), requestDto);
        //return 메세지 추가해야댐
        return ResponseEntity.ok().build();
    }

}
