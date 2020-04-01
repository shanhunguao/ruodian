package com.ncu.springboot.config.mq.client;

import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;

public class MqttClient {
	private MQTT mqtt;
	private FutureConnection futureConnection;
	private CallbackConnection callbackConnection;

	public MqttClient() {
	}

	public MqttClient(MQTT mqtt, FutureConnection futureConnection, CallbackConnection callbackConnection) {
		this.mqtt = mqtt;
		this.futureConnection = futureConnection;
		this.callbackConnection = callbackConnection;
	}

	public MQTT getMqtt() {
		return mqtt;
	}

	public MqttClient putMqtt(MQTT mqtt) {
		this.mqtt = mqtt;
		return this;
	}

	public FutureConnection getFutureConnection() {
		return futureConnection;
	}

	public MqttClient putFutureConnection(FutureConnection futureConnection) {
		this.futureConnection = futureConnection;
		return this;
	}

	public CallbackConnection getCallbackConnection() {
		return callbackConnection;
	}

	public MqttClient putCallbackConnection(CallbackConnection callbackConnection) {
		this.callbackConnection = callbackConnection;
		return this;
	}

	public void setMqtt(MQTT mqtt) {
		this.mqtt = mqtt;
	}

	public void setFutureConnection(FutureConnection futureConnection) {
		this.futureConnection = futureConnection;
	}

	public void setCallbackConnection(CallbackConnection callbackConnection) {
		this.callbackConnection = callbackConnection;
	}
}
