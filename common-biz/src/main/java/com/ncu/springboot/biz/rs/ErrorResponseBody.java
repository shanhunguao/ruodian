package com.ncu.springboot.biz.rs;

public class ErrorResponseBody extends BaseResponseBody {
	
	private final String SUCCESS_CODE = "1";
	private final String SUCCESS_MSG = "操作失败";
	
	private static ErrorResponseBody instance = null;
	
	private ErrorResponseBody() {
		super();
		this.setCode(SUCCESS_CODE);
		this.setMessage(SUCCESS_MSG);
	}
	
	public static ErrorResponseBody getInstance() {
		if (instance == null) {
			instance = new ErrorResponseBody();
		}
		
		return instance;
	}
	
}
