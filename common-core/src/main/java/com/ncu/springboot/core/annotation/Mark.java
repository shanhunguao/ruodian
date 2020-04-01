package com.ncu.springboot.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Mark {
	
	 public String name() default "";

}
