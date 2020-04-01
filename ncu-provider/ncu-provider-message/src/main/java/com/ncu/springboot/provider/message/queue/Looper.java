package com.ncu.springboot.provider.message.queue;

public class Looper {
	
	private MessageQueue mQueue;
	private MessageHandler mHandler;
	
	private Thread mThread;
	
	public Looper(MessageHandler handler) {
		mQueue = new MessageQueue();
		mHandler = handler;
		mHandler.setQueue(mQueue);
	}
	
	public void loop() {
		mThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (;;) {
					Message msg = mQueue.next();
					mHandler.handleMessage(msg);
				}
			}
		});
		
		mThread.start();
	}
	
	public MessageHandler getHandler() {
		return mHandler;
	}
	
	
}
