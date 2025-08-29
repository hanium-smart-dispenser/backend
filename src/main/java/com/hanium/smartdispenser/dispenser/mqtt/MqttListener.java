package com.hanium.smartdispenser.dispenser.mqtt;

import com.hanium.smartdispenser.common.JsonMapper;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.dto.DispenserCommandSimpleResponseDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserRegisterRequestDto;
import com.hanium.smartdispenser.dispenser.dto.DispenserStatusDto;
import com.hanium.smartdispenser.dispenser.service.DispenserService;
import com.hanium.smartdispenser.history.HistoryService;
import com.hanium.smartdispenser.history.domain.HistoryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.hanium.smartdispenser.dispenser.mqtt.MqttConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttListener {

    private final MqttClient mqttClient;

    private final JsonMapper mapper;
    private final DispenserService dispenserService;
    private final HistoryService historyService;


    @EventListener(ApplicationReadyEvent.class)
    public void subscribeStatus() throws MqttException {
        log.info("[MQTT] 토픽 구독 시작");
        mqttClient.subscribe(DISPENSER_STATUS_ALL, this::getDispenserStatus);
        mqttClient.subscribe(DISPENSER_COMMAND_RESPONSE_ALL, this::getDispenserCommandResponse);
        mqttClient.subscribe(DISPENSER_REGISTER_ALL, this::register);
        log.info("[MQTT] 토픽 구독 종료");
    }

    void getDispenserStatus(String topic, MqttMessage message) {
        log.info("[MQTT] 디스펜서 상태 조회");
        log.info("[MQTT][status] topic={}, qos={}, retained={}, len={}",
                topic, message.getQos(), message.isRetained(), message.getPayload().length);
        String payload = new String(message.getPayload());
        DispenserStatusDto dispenserStatusDto = mapper.fromJson(payload, DispenserStatusDto.class);
        dispenserService.updateDispenserSauces(dispenserStatusDto);
    }

    void getDispenserCommandResponse(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        DispenserCommandSimpleResponseDto responseDto = mapper.fromJson(payload, DispenserCommandSimpleResponseDto.class);
        HistoryStatus status = responseDto.status();

        if (status == HistoryStatus.SUCCESS) {
            historyService.updateHistoryStatus(responseDto.historyId(), HistoryStatus.SUCCESS);
            dispenserService.updateDispenserStatus(responseDto.dispenserId(), DispenserStatus.READY);
        } else {
            historyService.updateHistoryStatus(responseDto.historyId(), HistoryStatus.FAIL);
            dispenserService.updateDispenserStatus(responseDto.dispenserId(), DispenserStatus.ERROR);
        }
    }

    void register(String topic, MqttMessage message) {
        log.info("[MQTT] register 요청");
        String payload = new String(message.getPayload());

        DispenserRegisterRequestDto requestDto = mapper.fromJson(payload, DispenserRegisterRequestDto.class);

        if (!dispenserService.isExist(requestDto.uuid())) {
            dispenserService.createDispenser(Dispenser.of(DispenserStatus.READY, null, requestDto.uuid()));
        }
        log.info("[MQTT] register 응답 끝");
    }
}
