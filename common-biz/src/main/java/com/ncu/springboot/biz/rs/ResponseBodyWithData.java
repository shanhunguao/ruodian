package com.ncu.springboot.biz.rs;

public class ResponseBodyWithData<T> extends BaseResponseBody {
	
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
