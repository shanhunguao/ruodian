package com.ncu.springboot.core.context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 数据上下文，用于在一个线程上携带环境信息
 */
public class DataContext {
    private static ThreadLocal<DataContext> dataContext = ThreadLocal.withInitial(() -> new DataContext());

    private Map<String, Object> store = new HashMap<>();

    private static DataContext getDataContext() {
        return dataContext.get();
    }

    public static Map<String, Object> getStore() {
        return getDataContext().store;
    }

    public static Object get(String key) {
        return getStore().get(key);
    }

    public static Object set(String key, Object value) {
        return getStore().put(key, value);
    }

    public static Object remove(String key) {
        return getStore().remove(key);
    }

    public static void clear() {
        getStore().clear();
    }

}
