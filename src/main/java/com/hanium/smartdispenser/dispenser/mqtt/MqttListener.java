package com.hanium.smartdispenser.dispenser.mqtt;

import com.hanium.smartdispenser.common.JsonMapper;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandSimpleResponseDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserStatusDto;
import com.hanium.smartdispenser.dispenser.service.DispenserService;
import com.hanium.smartdispenser.dispenser.service.DispenserSauceService;
import com.hanium.smartdispenser.history.HistoryService;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import static com.hanium.smartdispenser.dispenser.mqtt.MqttConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttListener {

    private final MqttClient mqttClient;
    private final JsonMapper<DispenserStatusDto> statusMapper;
    private final JsonMapper<DispenserCommandSimpleResponseDto> reponseMapper;
    private final DispenserSauceService dispenserSauceService;
    private final DispenserService dispenserService;
    private final HistoryService historyService;

    @PostConstruct
    public void subscribeStatus() throws MqttException {
        mqttClient.subscribe(DISPENSER_STATUS_ALL, this::getDispenserStatus);
        mqttClient.subscribe(DISPENSER_COMMAND_RESPONSE_ALL, this::getDispenserCommandResponse);
    }

    void getDispenserStatus(String topic, MqttMessage message) {

        String payload = new String(message.getPayload());
        DispenserStatusDto dispenserStatusDto = statusMapper.fromJson(payload, DispenserStatusDto.class);
        dispenserSauceService.updateStatus(dispenserStatusDto);
    }
    void getDispenserCommandResponse(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        DispenserCommandSimpleResponseDto responseDto = reponseMapper.fromJson(payload, DispenserCommandSimpleResponseDto.class);
        HistoryStatus status = responseDto.getStatus();

        if (status == HistoryStatus.SUCCESS) {
            historyService.updateHistoryStatus(responseDto.getHistoryId(), HistoryStatus.SUCCESS);
            dispenserService.updateDispenserStatus(responseDto.getDispenserId(), DispenserStatus.READY);
        } else {
            historyService.updateHistoryStatus(responseDto.getHistoryId(), HistoryStatus.FAIL);
            dispenserService.updateDispenserStatus(responseDto.getDispenserId(), DispenserStatus.ERROR);
        }
    }
}
