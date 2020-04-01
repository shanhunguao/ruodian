package com.ncu.springboot.provider.message.init;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ncu.springboot.provider.message.service.MessageQueueService;

@Component
public class MessageQueueInitializer implements ApplicationListener<ContextRefreshedEvent> {

	@Resource
	private MessageQueueService messageQueueService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 启动消息队列服务
		messageQueueService.startService();
	}

}
