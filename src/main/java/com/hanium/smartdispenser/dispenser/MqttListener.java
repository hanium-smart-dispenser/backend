package com.hanium.smartdispenser.dispenser;

import com.hanium.smartdispenser.common.JsonMapper;
import com.hanium.smartdispenser.dispenser.dto.DispenserStatusDto;
import com.hanium.smartdispenser.dispenser.service.DispenserSourceService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttListener {

    private final MqttClient mqttClient;
    private final JsonMapper<DispenserStatusDto> mapper;
    private final DispenserSourceService dispenserSourceService;

    @PostConstruct
    public void subscribeStatus() throws MqttException {
        mqttClient.subscribe("dispenser/+/status", this::sendDispenserStatus);
    }

    void sendDispenserStatus(String topic, MqttMessage message) {

        String payload = new String(message.getPayload());
        DispenserStatusDto dispenserStatusDto = mapper.fromJson(payload, DispenserStatusDto.class);
        dispenserSourceService.updateStatus(dispenserStatusDto);
    }


}
