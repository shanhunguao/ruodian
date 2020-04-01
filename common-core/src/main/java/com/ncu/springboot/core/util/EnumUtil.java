package com.ncu.springboot.core.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ncu.springboot.core.constant.BasicErrorCodes;
import com.ncu.springboot.core.exception.NcuErrInfoException;

public class EnumUtil {
    private static Map<Class<? extends Enum>, Map<Object, ? extends Enum>> enumsMap = new HashMap<Class<? extends Enum>, Map<Object, ? extends Enum>>();

    private static Lock enumsMapLock = new ReentrantLock();

    public static <E extends Enum> Map<Object, E> getEnumMap(Class<E> enumClass) {
        Map<Object, E> enumMap = (Map<Object, E>) enumsMap.get(enumClass);
        if (null == enumMap) {
            enumsMapLock.lock();
            try {
                Method getValMethod = null;
                try {
                    getValMethod = enumClass.getDeclaredMethod("getVal");
                } catch (NoSuchMethodException e) {
                }
                try {
                    enumMap = new LinkedHashMap<>();
                    for (E e : enumClass.getEnumConstants()) {
                        // 根据是否有getVal方法来获取值
                        enumMap.put(null != getValMethod ? getValMethod.invoke(e) : e.name(), e);
                    }
                } catch (Exception e) {
                    throw new NcuErrInfoException(BasicErrorCodes.COMMON_ERROR);
                }
            } finally {
                enumsMapLock.unlock();
            }
        }
        return enumMap;
    }

    public static <E extends Enum<?>> E valOf(Object val, Class<E> enumClass) {
        Map<Object, E> enumMap = getEnumMap(enumClass);
        return enumMap.get(val);
    }

}
