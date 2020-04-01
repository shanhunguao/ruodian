package com.ncu.springboot.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import com.ncu.springboot.config.mq.MQConfig;
import com.ncu.springboot.config.mq.factory.MqttClientFactory;

@Configuration
public class ActiveMQConfig {
	
	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	
	@Value("${spring.activemq.user}")
	private String user;
	
	@Value("${spring.activemq.password}")
	private String password;
	
	@Value("${spring.activemq.pool.max-connections}")
	private int maxConnections;
	
	@Value("${spring.activemq.mqtt-url}")
	private String mqttURL;
	
	@Bean(name = "connectionFactory")
	public ConnectionFactory connectionFactory(){
		PooledConnectionFactory connectionFactory = new PooledConnectionFactory();
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		activeMQConnectionFactory.setUserName(user);
		activeMQConnectionFactory.setPassword(password);
		connectionFactory.setConnectionFactory(activeMQConnectionFactory);
		connectionFactory.setMaxConnections(maxConnections);
		return connectionFactory;
	}
	
	@Bean
	public MQConfig mqConfig() {
		MQConfig config = new MQConfig();
		config.putBrokerURL(brokerUrl).putUsername(user).putPassword(password).putMaxConnections(maxConnections).putMqttUrl(mqttURL);
		return config;
	}
	
	@Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        //关闭事物
        jmsTemplate.setSessionTransacted(false);
        //TODO 在此设置无效
//        jmsTemplate.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
        jmsTemplate.setConnectionFactory(connectionFactory());
        return jmsTemplate;
    }
	
	@Bean
	public JmsMessagingTemplate jmsMessageTemplate(){
		return new JmsMessagingTemplate(connectionFactory());
	}
	
}
