package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.auth.UserPrincipal;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandRequestDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandResponseDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserStatusDto;
import com.hanium.smartdispenser.dispenser.service.DispenserCommandFacade;
import com.hanium.smartdispenser.dispenser.service.DispenserService;
import com.hanium.smartdispenser.dispenser.service.DispenserSauceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/dispensers")
public class DispenserController {

    private final DispenserCommandFacade dispenserCommandFacade;
    private final DispenserService dispenserService;
    private final DispenserSauceService dispenserSauceService;

    @GetMapping("/me")
    public DispenserStatusDto sendDispenserInfo(@AuthenticationPrincipal UserPrincipal user) {
        Dispenser dispenser = dispenserService.findByUser(user.getUserId());
        return dispenserSauceService.getDispenserStatus(dispenser.getId());
    }

    @PostMapping("/me/command")
    public ResponseEntity<DispenserCommandResponseDto> sendCommand(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody DispenserCommandRequestDto requestDto) {

        //비회원 로직
        if (user == null) {
            DispenserCommandResponseDto responseDto = dispenserCommandFacade.simpleSendCommand(requestDto.getDispenserId(), requestDto.getRecipeId());
            return ResponseEntity.ok(responseDto);
        }

        Dispenser dispenser = dispenserService.findByUser(user.getUserId());
        DispenserCommandResponseDto responseDto = dispenserCommandFacade.sendCommand(
                dispenser.getId(), user.getUserId(), requestDto.getRecipeId());
        return ResponseEntity.ok(responseDto);
    }

}
