package com.hanium.smartdispenser.dispenser.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Bean
    public MqttClient mqttClient() throws MqttException {
        String brokerUrl = "tcp://localhost:1883";
        String clientId = "spring-client";

        MqttClient client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setAutomaticReconnect(true);

        client.connect(options);
        return client;
    }
}
