package com.ncu.springboot.core.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ncu.springboot.core.jdkextends.NcuStringBuilder;
import com.ncu.springboot.core.jdkextends.SetBuilder;

public class TypeUtil {
    private static final String NESTING_BY_POINT_MARK = ".";
    private static final String NESTING_BY_ARY_MARK_LEFT = "[";
    private static final String NESTING_BY_ARY_MARK_RIGHT = "]";
    private static final String DOUBLE_NESTING_BY_ARY_MARK = "][";          //通过数组实现双层嵌套
    private static final int NOT_FIND_MARK = -1;
    private static final Set<Class<?>> basicTypeSet = SetBuilder.getInstance().addAll(boolean.class, byte.class, short.class, char.class, int.class, long.class,
            float.class, double.class, Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class).toSet();
    private static final Set<Class<?>> basicTypeWithStringSet = SetBuilder.getInstance().addAll(basicTypeSet).add(String.class).toSet();

    public static <T> T cast(Object source, Class<T> targetClazz) {
        if (source == null) {
            return null;
        } else if (targetClazz == null) {
            throw new IllegalArgumentException("clazz is null");
        } else if (targetClazz == source.getClass()) {
            return (T) source;
        } else if (source instanceof Map && targetClazz != String.class) {
            return targetClazz == Object.class || targetClazz == Map.class ? (T) source : (T) castToJavaBean((Map) source, targetClazz);
        } else {
            if (targetClazz.isArray()) {
                if (source instanceof Collection) {
                    Collection sourceCollection = (Collection) source;
                    Object ary = Array.newInstance(targetClazz.getComponentType(), sourceCollection.size());
                    int idx = 0;
                    for (Object obj : sourceCollection) {
                        Array.set(ary, idx++, cast(obj, targetClazz.getComponentType()));
                    }
                    return (T) ary;
                }
                if (targetClazz == byte[].class) {
                    return (T) ValueUtil.getByte(source, null);
                }
            }

            while( CollectionUtil.isArrayOrCollection(source) ) {
                source = CollectionUtil.getFirst(source).get();
            }

            if (targetClazz.isAssignableFrom(source.getClass())) {
                return (T) source;
            } else if (isBoolean(targetClazz)) {
                return (T) ValueUtil.getBoolean(source, null);
            } else if (isByte(targetClazz)) {
                return (T) ValueUtil.getByte(source, null);
            } else if (isShort(targetClazz)) {
                return (T) ValueUtil.getShort(source, null);
            } else if (isInteger(targetClazz)) {
                return (T) ValueUtil.getIntObj(source, null);
            } else if (isLong(targetClazz)) {
                return (T) ValueUtil.getLong(source, null);
            } else if (isFloat(targetClazz)) {
                return (T) ValueUtil.getFloat(source, null);
            } else if (isDouble(targetClazz)) {
                return (T) ValueUtil.getDouble(source, null);
            }
            if (targetClazz == String.class) {
                return (T) ValueUtil.getString(source);
            } else {
                if (source instanceof String) {
                    String strVal = ValueUtil.getString(source);
                    if (StringUtil.isEmpty(strVal)) {
                        return null;
                    }
                    return TypeUtil.cast(JSONObject.parseObject(strVal) , targetClazz);
                }
                return null;
            }
        }
    }

    public static boolean isFloat(Class<?> clazz) {
        return clazz == Float.class || clazz == Float.TYPE;
    }

    public static boolean isDouble(Class<?> clazz) {
        return clazz == Double.class || clazz == Double.TYPE;
    }

    public static boolean isLong(Class<?> clazz) {
        return clazz == Long.class || clazz == Long.TYPE;
    }

    public static boolean isInteger(Class<?> clazz) {
        return clazz == Integer.class || clazz == Integer.TYPE;
    }

    public static boolean isShort(Class<?> clazz) {
        return clazz == Short.class || clazz == Short.TYPE;
    }

    public static boolean isByte(Class<?> clazz) {
        return clazz == Byte.class || clazz == Byte.TYPE;
    }

    public static boolean isBoolean(Class<?> clazz) {
        return clazz == Boolean.class || clazz == Boolean.TYPE;
    }

    public static <T> T cast(Object source , Type targetType) {
        if( targetType instanceof ParameterizedType ) {
            return castParameterized(source , (ParameterizedType) targetType);
        }
        return (T)cast(source , (Class)targetType);
    }

    public static <T> T castParameterized(Object obj, ParameterizedType type) {
        Type rawTye = type.getRawType();
        Type argType;
        if (CollectionUtil.inCollection(rawTye, Set.class, HashSet.class, TreeSet.class, List.class, ArrayList.class)) {
            argType = type.getActualTypeArguments()[0];
            if (obj instanceof Iterable) {
                Collection collection = rawTye != Set.class && rawTye != HashSet.class ? rawTye == TreeSet.class ? new TreeSet() : new ArrayList() : new HashSet();
                for(Iterator iterator = ((Iterable) obj).iterator() ; iterator.hasNext() ; ) {
                    collection.add( cast(iterator.next() , argType));
                }
                return (T) collection;
            }
        }
        if (rawTye == Map.class || rawTye == HashMap.class) {
            argType = type.getActualTypeArguments()[0];
            Type valueType = type.getActualTypeArguments()[1];
            if (obj instanceof Map) {
                HashMap map = new HashMap();
                Iterator item = ((Map) obj).entrySet().iterator();

                while (item.hasNext()) {
                    Entry entry = (Entry) item.next();
                    Object key = cast(entry.getKey(), argType);
                    Object value = cast(entry.getValue(), valueType);
                    map.put(key, value);
                }
                return (T) map;
            }
        }
        if (obj instanceof String) {
            String argType1 = (String) obj;
            if (argType1.length() == 0) {
                return null;
            }
        }
        if (type.getActualTypeArguments().length == 1) {
            argType = type.getActualTypeArguments()[0];
            if (argType instanceof WildcardType) {
                return cast(obj, rawTye);
            }
        }
        throw new JSONException("can not cast to : " + type);
    }

    public static <T> T castToJavaBean(Map<String, Object> sourceMap, Class<T> targetClazz) {
        Map<String,Object> paramMap = new HashMap(sourceMap);
        T result = ReflectionUtil.newInstance(targetClazz);
        try {
            Map<String, Field> fieldMap = ReflectionUtil.listAllFields(targetClazz);
            for (Iterator<Entry<String, Object>> iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
                Entry<String, Object> entry = iterator.next();
                String paramName = entry.getKey();
                ReflectionUtil.getField(fieldMap , paramName).ifPresent( field -> {
                    ReflectionUtil.setFieldValueRough(result, field, entry.getValue());
                    iterator.remove();
                    fieldMap.remove(paramName);
                } );
            }
            if (!CollectionUtil.isEmpty(paramMap)) {
                parseNestingObjectByPointOrAry(paramMap, fieldMap, result);
            }
        } catch (Exception e) {
        }
        return result;
    }

    private static void parseNestingObjectByPointOrAry(Map<String, Object> paramMap, Map<String, Field> fieldMap, Object result) {
    	NcuStringBuilder keyBuffer = NcuStringBuilder.getInstance();
        paramMap.forEach( (key , value) -> {
            keyBuffer.clear().append(key);
            if (NOT_FIND_MARK != keyBuffer.indexOf(DOUBLE_NESTING_BY_ARY_MARK)) {
                StringUtil.replaceAll(keyBuffer, NESTING_BY_POINT_MARK, DOUBLE_NESTING_BY_ARY_MARK);
            }
            if (NOT_FIND_MARK != keyBuffer.indexOf(NESTING_BY_ARY_MARK_LEFT)) {
                StringUtil.replaceAll(keyBuffer, NESTING_BY_POINT_MARK, NESTING_BY_ARY_MARK_LEFT);
                StringUtil.replaceAll(keyBuffer, "", NESTING_BY_ARY_MARK_RIGHT);
            }
            int pointIdx = keyBuffer.indexOf(NESTING_BY_POINT_MARK);
            if (NOT_FIND_MARK != pointIdx) {
                String fieldName = keyBuffer.subString(0, pointIdx);       //field.3.x中取出field
                ReflectionUtil.getField(fieldMap , fieldName).ifPresent(field ->
                        parseNestingObjectByPoint(keyBuffer.delete(0, pointIdx + 1), value, field, result) );
            }
        } );
    }

    private static void parseNestingObjectByPoint(NcuStringBuilder keyBuffer, Object value, Field field, Object resultObj) {
        String listIdx = null;
        Integer pointIndex = keyBuffer.indexOf(NESTING_BY_POINT_MARK);
        if (NOT_FIND_MARK != pointIndex) {
            listIdx = keyBuffer.subString(0, pointIndex);               //例如 field.3.x 中取出3
        }
        if (ValueUtil.isInt(listIdx) && field.getType().equals(List.class)) {
            parseNestingObjectToList(keyBuffer.delete(0, pointIndex + 1), value, field, resultObj, ValueUtil.getInt(listIdx));
        }
    }

    private static void parseNestingObjectToList(NcuStringBuilder keyBuffer, Object value, Field field, Object result, int index) {
        List resultList = (List) ReflectionUtil.getValueRough(result, field);
        Type[] typeAry = ReflectionUtil.getGenerictActualType(field.getGenericType());
        Object defaultValue;
        if (null != typeAry && 1 == typeAry.length) {
            Type type = typeAry[0];
            if (null == resultList) {
                resultList = new ArrayList();
            }
            if (resultList.size() <= index) {
                for (int i = resultList.size(); i <= index; i++) {
                    defaultValue = ReflectionUtil.getPrimitiveDefaultValue((Class<?>) type);
                    if (null == defaultValue) {
                        defaultValue = ReflectionUtil.newInstance((Class<? extends Object>) type);
                    }
                    resultList.add(i, defaultValue);
                }
            }
            Object itemValue = resultList.get(index);
            ReflectionUtil.setFieldValueRough(itemValue, keyBuffer.toString(), value);
            resultList.set(index, itemValue);
            ReflectionUtil.setFieldValueRough(result, field, resultList);
        }
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            getClass(((ParameterizedType) type).getRawType());
        }
        return Object.class;
    }

    public static boolean isBasicDataType(Class<?> clazz) {
        return basicTypeSet.contains(clazz);
    }

    public static boolean isBasicDataTypeOrString(Class<?> clazz) {
        return basicTypeWithStringSet.contains(clazz);
    }
}