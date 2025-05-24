package com.hanium.smartdispenser.dispenser.mqtt;

import com.hanium.smartdispenser.dispenser.exception.DispenserCommandSendFailedException;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import static com.hanium.smartdispenser.dispenser.mqtt.MqttConstants.getDispenserCommandTopic;

@Service
@RequiredArgsConstructor
public class MqttService {

    private final MqttClient mqttClient;

    public void sendCommand(Long dispenserId, String payload) {
        String topic = getDispenserCommandTopic(dispenserId);

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
