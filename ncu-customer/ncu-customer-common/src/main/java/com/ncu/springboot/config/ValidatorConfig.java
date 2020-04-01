package com.ncu.springboot.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class ValidatorConfig {
	
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
				.configure()
				.failFast(true)
				.buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
	
}
