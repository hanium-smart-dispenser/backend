package com.hanium.smartdispenser.dispenser.mqtt;

public class MqttConstants {

    public static String getDispenserCommandTopic(Long dispenserId) {
        return "dispenser/" + dispenserId + "/command";
    }

    public static final String DISPENSER_STATUS_ALL = "dispenser/+/status";
    public static final String DISPENSER_COMMAND_RESPONSE_ALL = "dispenser/+/command/response";
}
