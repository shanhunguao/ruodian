package com.ncu.springboot.controller;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQQueue;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncu.springboot.config.mq.client.MqttClient;
import com.ncu.springboot.config.mq.factory.MqttClientFactory;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@RequestMapping("/sendMessage")
	public void sendMessage(HttpServletRequest request, String queueName, String msg) {
		System.out.println(request.getSession().getAttribute("zhangjun"));
		long startTime = System.currentTimeMillis();
		Destination destination = new ActiveMQQueue(queueName);
		this.jmsMessagingTemplate.convertAndSend(destination, msg);
		long endTime = System.currentTimeMillis();
		System.out.println("time is " + (endTime - startTime));

		MqttClient mqttClient = MqttClientFactory.getInstance().getMqttClient("zhangjun");
//		mqttClient.getFutureConnection().publish("zhangjun", new String("aaaaa").getBytes(), QoS.AT_MOST_ONCE, false);
		Topic[] topics = { new Topic("zhangjun", QoS.AT_MOST_ONCE), };
		mqttClient.getFutureConnection().subscribe(topics);

		CallbackConnection connection = mqttClient.getCallbackConnection();

		connection.listener(new Listener() {

			@Override
			public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
				System.out.println("topic is " + topic);
				System.out.println("body is " + body);
			}

			@Override
			public void onFailure(Throwable value) {
				System.out.println("onFailure");
			}

			@Override
			public void onDisconnected() {
				System.out.println("onDisconnected");
			}

			@Override
			public void onConnected() {
				System.out.println("onConnected");
			}
		});

		connection.connect(new Callback<Void>() {

			@Override
			public void onSuccess(Void value) {
				connection.publish("zhangjun", new String("aaaaa").getBytes(), QoS.AT_MOST_ONCE, false,
						new Callback<Void>() {

							@Override
							public void onSuccess(Void value) {
								System.out.println("onSuccess");
							}

							@Override
							public void onFailure(Throwable value) {
								// TODO Auto-generated method stub

							}
						});
			}

			@Override
			public void onFailure(Throwable value) {
				// TODO Auto-generated method stub

			}
		});

	}

	@RequestMapping("/sendMessage2")
	public void sendMessageTwo(HttpServletRequest request, HttpServletResponse response, String queueName, String msg) {
		request.getSession().setAttribute("zhangjun", "zhangzhang");
//		response.addCookie(new Cookie("JSESSIONID", request.getSession().getId()));
	}

}
