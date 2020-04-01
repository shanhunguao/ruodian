package com.ncu.springboot.api.message.template;

public class MessageTemplate {

	public static final String SMSCODE = "您的验证码是%s，在5分钟内有效，如非本人操作请忽略本短信。";
	
	public static final String MODIFY_PASSWORD_SMSCODE = "尊敬的%s您好, 您本次修改密码的安全验证码是%s";

	public static String generateMessage(String template, String... args) {
		StringBuffer result = new StringBuffer(template);
		String rep = "%s";
		for (String arg : args) {
			int start = result.indexOf(rep);
			int end = start + rep.length();
			result.replace(start, end, arg);
		}
		return result.toString();
	}
}
