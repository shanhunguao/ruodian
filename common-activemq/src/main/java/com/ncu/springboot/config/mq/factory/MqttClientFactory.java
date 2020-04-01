package com.ncu.springboot.config.mq.factory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncu.springboot.config.mq.MQConfig;
import com.ncu.springboot.config.mq.client.MqttClient;

@Component
public class MqttClientFactory {
	
	@Autowired
	private MQConfig mqConfig;
	
	private Map<String, MqttClient> topicNameMqttMap = new HashMap<>();
    private Lock destinationMqttMapLock = new ReentrantLock();
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttClientFactory.class);
    private static MqttClientFactory factory;
    private static Lock factoryLock = new ReentrantLock();

    private MqttClientFactory() {
        try {
            initMqtt();
        } catch (Exception e) {
            LOGGER.error("init mqtt error ", e);
        }
    }

    public static MqttClientFactory getInstance() {
        if (null == factory) {
            try {
                factoryLock.lock();
                if (null == factory) {
                    factory = new MqttClientFactory();
                }
            } finally {
                factoryLock.unlock();
            }
        }
        return factory;
    }

    private MqttClient initMqtt() throws Exception {
        MQTT mqtt = new MQTT();
//        mqtt.setHost(new URI(mqConfig.getBrokerURL()));
//        mqtt.setUserName(mqConfig.getUsername());
//        mqtt.setPassword(mqConfig.getPassword());
        mqtt.setHost(new URI("tcp://127.0.0.1:1883"));
        mqtt.setUserName("admin");
        mqtt.setPassword("admin");
        mqtt.setCleanSession(true);
        mqtt.setReconnectAttemptsMax(6);
        mqtt.setReconnectDelay(2000);
        mqtt.setKeepAlive((short) 30);
        mqtt.setSendBufferSize(2*1024*1024);  //2M
        FutureConnection futureConnection = mqtt.futureConnection();
        futureConnection.connect().await();
        CallbackConnection callbackConnection = mqtt.callbackConnection();
        return new MqttClient(mqtt, futureConnection, callbackConnection);
    }

    public MqttClient getMqttClient(String topicName) {
        try {
            destinationMqttMapLock.lock();
            if (topicNameMqttMap.containsKey(topicName)) {
                return topicNameMqttMap.get(topicName);
            }
            MqttClient client;
            topicNameMqttMap.put(topicName, (client = initMqtt()));
            return client;
        } catch (Exception e) {
            LOGGER.error("get mqtt client error ", e);
            return null;
        } finally {
            destinationMqttMapLock.unlock();
        }
    }
}
