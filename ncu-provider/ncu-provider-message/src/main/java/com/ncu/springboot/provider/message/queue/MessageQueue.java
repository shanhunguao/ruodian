package com.ncu.springboot.provider.message.queue;

public class MessageQueue {

	// 消息队列中的消息头
	private Message mMessages = null;

	// 队列是否处于等待状态״̬
	private boolean mBlocked = true;

	// 标识消息队列是否退出，暂停服务
	private boolean mQuitting = false;;

	public boolean ismQuitting() {
		return mQuitting;
	}

	public void setmQuitting(boolean mQuitting) {
		this.mQuitting = mQuitting;
	}

	// 消息入队列
	public boolean enqueueMessage(Message msg, long when) {
		synchronized (this) {
			if (mQuitting) {
				IllegalStateException e = new IllegalStateException("��Ϣ������ͣʹ�ã����Ժ��ٷ�����Ϣ��");
				e.printStackTrace();
				return false;
			}

			msg.when = when;
			Message p = mMessages;
			boolean needWake = false;
			// 添加消息到链表中
			//时间近或者消息头为空时插入为第一
			if (p == null || when == 0 || when < p.when) {
				msg.next = p;
				mMessages = msg;
				needWake = mBlocked;
			} else {
				Message prev;
				for (;;) {
					prev = p;
					p = p.next;
					if (p == null || when < p.when) {
						break;
					}
				}
				msg.next = p;
				prev.next = msg;
			}

			if (needWake) {
				notify();
			}
		}

		return true;
	}

	// 消息出队列
	public Message next() {
		for (;;) {
			synchronized (this) {
				final long now = System.currentTimeMillis();
				Message msg = mMessages;
				try {
					if (msg != null) {
						if (now < msg.when) {
							System.out.println("wait( " + (msg.when - now) + "ms )");
							mBlocked = true;
							wait(msg.when - now);
						} else {
							mBlocked = false;
							mMessages = msg.next;
							msg.next = null;
							return msg;
						}
					} else {
						System.out.println("wait");
						mBlocked = true;
						wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
