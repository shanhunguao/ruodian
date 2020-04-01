package com.ncu.springboot.biz.rs;

public class SuccessResponseBody extends BaseResponseBody {
	
	private final String SUCCESS_CODE = "0";
	private final String SUCCESS_MSG = "操作成功";
	
	private static SuccessResponseBody instance = null;
	
	private SuccessResponseBody() {
		super();
		this.setCode(SUCCESS_CODE);
		this.setMessage(SUCCESS_MSG);
	}
	
	public static SuccessResponseBody getInstance() {
		if (instance == null) {
			instance = new SuccessResponseBody();
		}
		
		return instance;
	}
	
}
