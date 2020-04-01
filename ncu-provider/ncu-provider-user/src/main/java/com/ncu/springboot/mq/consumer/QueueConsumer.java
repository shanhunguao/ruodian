package com.ncu.springboot.mq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {
	
	@JmsListener(destination = "zhangjun")
	public void receiveQueue(String text) {
		System.out.println(text);
	}
	
	@JmsListener(destination = "zhangzixuan")
	public void handleQueue(String text) {
		System.out.println(text);
	}
	
}
