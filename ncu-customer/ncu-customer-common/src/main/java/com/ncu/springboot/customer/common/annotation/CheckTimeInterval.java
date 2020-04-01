package com.ncu.springboot.customer.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ncu.springboot.customer.common.validator.CheckTimeIntervalValidator;

@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER, TYPE_USE })
@Constraint(validatedBy = CheckTimeIntervalValidator.class)
public @interface CheckTimeInterval {
	
	String startTime() default "from";
	String endTime() default "to";
	
	String message() default "开始时间不能大于结束时间";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}
