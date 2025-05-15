package com.hanium.smartdispenser.dispenser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/dispensers")
public class DispenserController {

    private final DispenserService dispenserService;

    @PostMapping("/{dispenserId}/command")
    public ResponseEntity<DispenserCommandResponseDto> sendCommand(
            @PathVariable Long dispenserId,
            @RequestBody DispenserCommandRequestDto requestDto) {
        dispenserService.sendCommand(dispenserId, requestDto.getUserId(), requestDto);
        //return 메세지 추가해야댐
        return ResponseEntity.ok().build();
    }

}
