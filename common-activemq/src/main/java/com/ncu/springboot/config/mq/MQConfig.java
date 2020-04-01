package com.ncu.springboot.config.mq;

public class MQConfig {
	protected String brokerURL;
	protected String username;
	protected String password;
	protected String mqttURL;
	protected int maxConnections;

	public String getBrokerURL() {
		return brokerURL;
	}

	public MQConfig putBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public MQConfig putUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public MQConfig putPassword(String password) {
		this.password = password;
		return this;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMqttURL() {
		return mqttURL;
	}

	public void setMqttURL(String mqttURL) {
		this.mqttURL = mqttURL;
	}

	public MQConfig putMqttUrl(String mqttURL) {
		this.mqttURL = mqttURL;
		return this;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public MQConfig putMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
		return this;
	}

}
