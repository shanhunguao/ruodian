package com.ncu.springboot.provider.message.queue;

public class MessageStatus {
	public static final int WAIT_CHECK = 0;
	public static final int CHECK_FAIL = 1;
	public static final int WAIT_SEND = 2;
	public static final int SENT = 3;
	public static final int FAIL = 4;
	public static final int PART_FAIL = 5;
}
