package com.ncu.springboot.provider.message.constants;

public interface ValidateCodeConstants {
	
	int SMS_RANDOM_SIZE = 6;        //短信验证码位数
	int EMAIL_RANDOM_SIZE = 6;      //邮箱验证码位数
	
	int SMS_EXPIRE_SECOND = 300;    //单位秒
	int EMAIL_EXPIRE_SECOND = 300;
	
}
