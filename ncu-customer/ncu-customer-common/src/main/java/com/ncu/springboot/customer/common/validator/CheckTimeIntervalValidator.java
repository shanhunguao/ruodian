package com.ncu.springboot.customer.common.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.ncu.springboot.customer.common.annotation.CheckTimeInterval;

public class CheckTimeIntervalValidator implements ConstraintValidator<CheckTimeInterval, Object> {
	
	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	private String startTime;
	private String endTime;
	
	@Override
	public void initialize(CheckTimeInterval constraintAnnotation) {
		this.startTime = constraintAnnotation.startTime();
		this.endTime = constraintAnnotation.endTime();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		String start = (String) beanWrapper.getPropertyValue(startTime);
		String end = (String) beanWrapper.getPropertyValue(endTime);
		
		if (null == start || null == end) {
			return true;
		}
		
		int result = -1;
		try {
			result = (format1.parse(end)).compareTo(format1.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result >= 0) {
			return true;
		}
		
		return false;
	}
	
}
