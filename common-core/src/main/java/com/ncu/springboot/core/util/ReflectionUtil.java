package com.ncu.springboot.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.ncu.springboot.core.annotation.AllowToMap;
import com.ncu.springboot.core.jdkextends.NcuOptional;

public class ReflectionUtil {
    private static final Class<?> ROOT_CLASS = Object.class;
    private static final Map<Class<?> , Map<String,Field>> clazz2FieldMap = new HashMap<>();

    public static void makeAccessible(Field field, boolean acccessible) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass()
                .getModifiers())) && !field.isAccessible()) {
            field.setAccessible(acccessible);
        }
    }

    private static Field findField(Object bean, String fieldName) {
        if (null == fieldName) {
            return null;
        }
        try {
            Class<?> clazz = bean.getClass();
            do {
                Field[] fields = clazz.getDeclaredFields();
                int len = fields.length;
                for (int i = 0; i < len; i++) {
                    Field field = fields[i];
                    if (fieldName.equals(field.getName())) {
                        return field;
                    }
                }
            } while (null != (clazz = clazz.getSuperclass()));
        } catch (Exception e) {
            // 不打印bean，万一有敏感数据
        }
        return null;
    }

    public static String getClassName(Object obj) {
        if (obj == null)
            return StringUtil.EMPTY_STRING;
        return ValueUtil.getString(obj.getClass().getName());
    }

    public static String getClassSimpleName(Object obj) {
        if (obj == null)
            return StringUtil.EMPTY_STRING;
        return ValueUtil.getString(obj.getClass().getSimpleName());
    }

    public static void makeAccessible(Field field) {
        makeAccessible(field, true);
    }

    public static Map<String, Field> listAllFields(Class<?> clazz) {
        if( clazz2FieldMap.containsKey(clazz) ) {
            return new HashMap<>(clazz2FieldMap.get(clazz));
        }
        Map<String,Field> fieldMap = listAllFields(null, clazz);
        clazz2FieldMap.put(clazz , fieldMap);
        return new HashMap<>(fieldMap);
    }

    private static Map<String, Field> listAllFields(Map<String, Field> fieldMap, Class<?> clazz) {
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
        }
        if (clazz == ROOT_CLASS) {
            return new HashMap<>(fieldMap);
        }
        for (Field field : clazz.getDeclaredFields()) {
            if( !Modifier.isFinal(field.getModifiers()) ) {
                if (field.isAnnotationPresent(JSONField.class)) {
                    String jsonFieldName = field.getAnnotationsByType(JSONField.class)[0].name();
                    if (!StringUtil.isEmpty(jsonFieldName)) {
                        CollectionUtil.addIfNotExist(fieldMap, jsonFieldName, field);
                        continue;
                    }
                }
                CollectionUtil.addIfNotExist(fieldMap, field.getName(), field);
            }
        }
        fieldMap.putAll(listAllFields(fieldMap, clazz.getSuperclass()));
        return new HashMap<>(fieldMap);
    }

    public static void setFieldValueRough(Object obj, String fieldName, Object value) {
        getField(obj.getClass() , fieldName).ifPresent( field -> setFieldValueRough(obj, field, value) );
    }

    public static void setFieldValueRough(Object obj, Field field, Object value) {
        boolean isAccessible = field.isAccessible();
        try {
            makeAccessible(field);
            value = TypeUtil.cast(value, field.getGenericType());
            field.set(obj, value);
        } catch (Exception e) {
        } finally {
            makeAccessible(field, isAccessible);
        }
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        setFieldValueRough(obj, findField(obj, fieldName), value);
    }

    public static Object getValue(Object obj, Field field) {
        return getValue(obj, field.getName());
    }

    public static Object getValueRough(Object obj, Field field) {
        boolean isAccessible = field.isAccessible();
        try {
            makeAccessible(field);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        } finally {
            makeAccessible(field, isAccessible);
        }
    }

    public static Object getValue(Object obj, String fieldName) {
        try {
            Method readMethod;
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            if (null != pd && null != (readMethod = pd.getReadMethod())) {
                return readMethod.invoke(obj);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean isSubClass(Class<?> subClazz, Class<?> superClazz) {
        return superClazz.isAssignableFrom(subClazz);
    }

    public static Object getFieldValue(Object bean, String fieldName) {
        Field field = findField(bean, fieldName);
        if (null != field) {
            try {
                ReflectionUtil.makeAccessible(field);
                return field.get(bean);
            } catch (Exception e) {
                // 不打印bean，万一有敏感数据
            }
        }
        return null;
    }

    public static Object getProperty(Object bean, String propertyName, boolean tryField) {
        try {
            PropertyDescriptor propertyDescriptor = findPropertyDescriptor(bean, propertyName);
            if (null == propertyDescriptor) {
                if (tryField) {
                    return getFieldValue(bean, propertyName);
                }
            } else {
                return propertyDescriptor.getReadMethod().invoke(bean);
            }
        } catch (Exception e) {
            // 不打印bean，万一有敏感数据
        }
        return null;
    }

    private static PropertyDescriptor findPropertyDescriptor(Object bean, String propertyName) {
        try {
            if (null == bean || StringUtils.isBlank(propertyName)) {
                return null;
            }
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyName.equals(propertyDescriptor.getName())) {
                    return propertyDescriptor;
                }
            }
        } catch (Exception e) {
            // 不打印bean，万一有敏感数据
        }
        // 找不到对应的属性则返回null
        return null;
    }

    public static Object getProperty(Object bean, String propertyName) {
        return getProperty(bean, propertyName, false);
    }

    public static <T> T[] newArray(Class<T> clazz, int size) {
        return (T[]) Array.newInstance(clazz, size);
    }

    public static <T> T[][] newArray(Class<T> clazz, int firstLen, int secondLen) {
        return (T[][]) Array.newInstance(clazz, firstLen, secondLen);
    }

    public static <T> T newInstance(String className, Class<T> clazz) {
        try {
            if (clazz == null) {
                return (T) Class.forName(className).newInstance();
            }
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz) {
        return newInstance("", clazz);
    }

    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Field> fieldMap = listAllFields(obj.getClass());
        for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
            result.put(entry.getKey(), getValueRough(obj, entry.getValue()));
        }
        return result;
    }

    public static Map<String, Object> beanToMap(Object obj, String allowToMap) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Field> fieldMap = listAllFields(obj.getClass());
        Field field;
        for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
            field = entry.getValue();
            if (field.isAnnotationPresent(AllowToMap.class)) {
                String[] ary = field.getAnnotation(AllowToMap.class).value();
                if (CollectionUtil.inCollection(allowToMap, ary)) {
                    result.put(entry.getKey(), getValueRough(obj, entry.getValue()));
                }
            }
        }
        return result;
    }

    /**
     * 如果有type有泛型,则取出type的泛型类型,否则返回null
     */
    public static Type[] getGenerictActualType(Type type) {
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments();
        }
        return null;
    }

    public static Object getPrimitiveDefaultValue(Class<?> clazz) {
        if (TypeUtil.isBasicDataType(clazz)) {
            if (Boolean.class == clazz) {
                return false;
            }
            if (Byte.class == clazz) {
                return (byte) 0;
            }
            if (Short.class == clazz) {
                return (short) 0;
            }
            if (Character.class == clazz) {
                return (char) 0;
            }
            if (Integer.class == clazz) {
                return 0;
            }
            if (Long.class == clazz) {
                return 0L;
            }
            if (Float.class == clazz) {
                return 0F;
            }
            if (Double.class == clazz) {
                return 0D;
            }
        }
        return null;
    }

    public static NcuOptional<Field> getField(Map<String,Field> fieldMap, String fieldName) {
        if( fieldMap.containsKey(fieldName) ) {
            return NcuOptional.of(fieldMap.get(fieldName));
        }
        for(Map.Entry<String,Field> entry : fieldMap.entrySet()) {
            if( fieldName.equalsIgnoreCase(entry.getKey()) ) {
                return NcuOptional.of(entry.getValue());
            }
        }
        return NcuOptional.empty();
    }

    public static NcuOptional<Field> getField(Class<?> clazz, String fieldName) {
        Map<String,Field> fieldMap = listAllFields(clazz);
        return getField(fieldMap , fieldName);
    }

    public static Method getDeclaredMethod(Object obj, String methodName) {
        Method[] methodAry = obj.getClass().getDeclaredMethods();
        for (Method method : methodAry) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static Object invoke(Object targetObj, Method method, Object... param) {
        try {
            return method.invoke(targetObj, param);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T transClazz(Object sourceObj, Class<T> targetClazz) {
        if (sourceObj == null || targetClazz == null) {
        }
        Map<String, Object> sourceMap = beanToMap(sourceObj);
        return TypeUtil.cast(sourceMap, targetClazz);
    }
}
