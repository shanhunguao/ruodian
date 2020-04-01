package com.ncu.springboot.handler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcException;
import com.ncu.springboot.biz.error.ExceptionErrorCode;
import com.ncu.springboot.biz.rs.ResponseBodyWithData;

@ControllerAdvice
public class GlobalExceptionHandler {
    
	@ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseBodyWithData<String> constraintViolationExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
    	ResponseBodyWithData responseBodyWithData = new ResponseBodyWithData<String>();
    	responseBodyWithData.setData(request.getRequestURL().toString());
		responseBodyWithData.setCode("1");
		responseBodyWithData.setMessage(((ConstraintViolationException) e).getConstraintViolations().iterator().next().getMessageTemplate());
        return responseBodyWithData;
    }
	
	@ExceptionHandler(RpcException.class)
    @ResponseBody
    public ResponseBodyWithData<String> rpcExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
    	ResponseBodyWithData responseBodyWithData = new ResponseBodyWithData<String>();
    	responseBodyWithData.setData(request.getRequestURL().toString());
		responseBodyWithData.setCode("1");
		responseBodyWithData.setMessage("dubbo服务调用失败，请重试!");
		e.printStackTrace();
        return responseBodyWithData;
    }
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseBodyWithData<String> ExceptionHandler(HttpServletRequest request, Exception e) throws Exception {
		ResponseBodyWithData responseBodyWithData = new ResponseBodyWithData<String>();
		responseBodyWithData.setData(request.getRequestURL().toString());
		if (e instanceof NullPointerException) {
			responseBodyWithData.setCode(String.valueOf(ExceptionErrorCode.NULLEXCEPTION_ERROR.getErrorCode()));
			responseBodyWithData.setMessage(ExceptionErrorCode.NULLEXCEPTION_ERROR.getErrorMsg());
			e.printStackTrace();
		} else {
			e.printStackTrace();
			responseBodyWithData.setCode(String.valueOf(ExceptionErrorCode.PARAM_ERROR.getErrorCode()));
			responseBodyWithData.setMessage(ExceptionErrorCode.PARAM_ERROR.getErrorMsg());
		}
		return responseBodyWithData;
	}
    
}
