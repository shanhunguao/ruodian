package com.ncu.springboot.config.mq.type;

public enum MessageType {
	VEHICLE("vehicle", "vehicleMqttTemplate", "/fitkits/mqtt/status"),
    WEBSOCKET("webSocket", "webSocketJmsTemplate", "/wcs_server/websocket");

    private String prefix;
    private String name;
    private String queueName;

    MessageType(String prefix, String name, String queueName) {
        this.prefix = prefix;
        this.name = name;
        this.queueName = queueName;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public String getQueueName() {
        return queueName;
    }
}
