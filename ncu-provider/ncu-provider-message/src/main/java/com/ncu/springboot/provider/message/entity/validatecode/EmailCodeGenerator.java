package com.ncu.springboot.provider.message.entity.validatecode;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.ncu.springboot.provider.message.constants.ValidateCodeConstants;

@Component
public class EmailCodeGenerator implements ValidateCodeGenerator {

	@Override
	public ValidateCode generate() {
		String code = RandomStringUtils.randomNumeric(ValidateCodeConstants.EMAIL_RANDOM_SIZE);
		return new ValidateCode(code, LocalDateTime.now().plusSeconds(ValidateCodeConstants.EMAIL_EXPIRE_SECOND));
	}

}
