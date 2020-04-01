package com.ncu.springboot.provider.message.queue;

public class Message<T extends MessageBody> {

	//消息id
	private Integer messageId;
	
	// 消息类型
	public int what;

	// 消息处理时间点，消息队列按该属性排序
	long when;

	T data;

	Message<T> next;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public int getWhat() {
		return what;
	}

	public void setWhat(int what) {
		this.what = what;
	}

	public long getWhen() {
		return when;
	}

	public void setWhen(long when) {
		this.when = when;
	}

	public Message<T> getNext() {
		return next;
	}

	public void setNext(Message<T> next) {
		this.next = next;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Message [what=" + what + ", when=" + when + ", data=" + data + "]";
	}

}
