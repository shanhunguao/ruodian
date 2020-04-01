package com.ncu.springboot.provider.message.service;

import com.ncu.springboot.provider.message.queue.Message;

public interface MessageQueueService {
	
	void startService();
	boolean enqueueMessage(Message msg);
	
}
