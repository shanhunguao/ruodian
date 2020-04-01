package com.ncu.springboot.biz.rs;

public class Res extends ResponseBodyWithData{
	
	public final static String SUCCESS_CODE = "0";
	public final static String SUCCESS_MSG = "success";
	
	public final static String ERROR_CODE = "1";
	public final static String ERROR_MSG = "ERROE";
	
	
	
	public Res() {
		super();
	}
	
	public static Res Res(String code,String msg,Object data) {
		Res res = new Res();
		res.setCode(code);
		res.setMessage(msg);
		res.setData(data);
		return res;
	}
	
	
	public static Res SUCCESS(Object data) {
		Res res = new Res();
		res.setCode(SUCCESS_CODE);
		res.setMessage(SUCCESS_MSG);
		res.setData(data);
		return res;
	}
	
	public static Res SUCCESS() {
		Res res = new Res();
		res.setCode(SUCCESS_CODE);
		res.setMessage(SUCCESS_MSG);
		return res;
	}
	
	public static Res SUCCESS(String msg,Object data) {
		Res res = new Res();
		res.setCode(SUCCESS_CODE);
		res.setMessage(msg);
		res.setData(data);
		return res;
	}
	
	public static Res SUCCESS(String msg) {
		Res res = new Res();
		res.setCode(SUCCESS_CODE);
		res.setMessage(msg);
		return res;
	}
	
	
	
	public static Res ERROR() {
		Res res = new Res();
		res.setCode(ERROR_CODE);
		res.setMessage(ERROR_MSG);
		return res;
	}
	
	public static Res ERROR(String msg) {
		Res res = new Res();
		res.setCode(ERROR_CODE);
		res.setMessage(msg);
		return res;
	}
	
	public static Res ERROR(String code,String msg) {
		Res res = new Res();
		res.setCode(code);
		res.setMessage(msg);
		return res;
	}
	

}
