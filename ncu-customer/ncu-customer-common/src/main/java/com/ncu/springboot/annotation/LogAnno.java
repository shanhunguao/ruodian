package com.ncu.springboot.annotation;

/**
 * @Created by zhoufan
 * @Date 2019/11/6 16:18
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author lastwhisper
 */
@Target(ElementType.METHOD) // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
public @interface LogAnno {
    String operateType();// 记录日志的操作类型
}
