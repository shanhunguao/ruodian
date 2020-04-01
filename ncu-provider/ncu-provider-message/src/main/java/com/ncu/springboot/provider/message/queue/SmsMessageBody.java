package com.ncu.springboot.provider.message.queue;

//短信消息
public class SmsMessageBody extends MessageBody {

	// 收件人手机号
	private String mobile;

	// 发送内容
	private String content;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SmsMessageBody [mobile=" + mobile + ", content=" + content + "]";
	}

}
