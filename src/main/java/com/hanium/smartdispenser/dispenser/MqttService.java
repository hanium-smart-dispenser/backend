package com.hanium.smartdispenser.dispenser;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttService {

    private final MqttClient mqttClient;

    public void sendCommand(Long dispenserId, String payload) {
        String topic = "dispenser/" + dispenserId + "/command";

        if (!mqttClient.isConnected()) {
            try {
                mqttClient.reconnect();
            } catch (MqttException e) {
                throw new DispenserCommandSendFailedException(e);
            }
        }

        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        message.setRetained(false);

        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            throw new DispenserCommandSendFailedException(e);
        }
    }
}
