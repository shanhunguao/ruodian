package com.ncu.springboot.core.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.ncu.springboot.core.constant.CharsetType;

public class ValueUtil {


    private static final BigDecimal BIG_DECIMAL_ZERO = BigDecimal.ZERO;

    /********* getString-start **********/
    public static String getString(Object object, String defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return String.valueOf(object);
    }

    public static String getString(Object object, String defaultValue, boolean emptyIsNull) {
        if (object == null) {
            return defaultValue;
        }
        String result = String.valueOf(object);
        return emptyIsNull && StringUtil.isEmpty(result) ? defaultValue : result;
    }

    public static String getString(Object object) {
        return getString(object, "");
    }

    public static String getString(Map<String, Object> map, String key, String defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        return getString(map.get(key), defaultValue);
    }

    public static String getString(Map<String, Object> map, String key) {
        return getString(map.get(key));
    }
    /********* getString-end **********/

    /********* getInt-start **********/
    public static int getInt(Map<String, Object> map, String key, int defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        return getInt(map.get(key), defaultValue);
    }

    public static int getInt(Object object, int defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getInt(Object object) {
        return getInt(object, 0);
    }

    public static Integer getIntObj(Object object, Integer defaultValue) {
        Integer result;
        return defaultValue == null & Integer.MIN_VALUE == (result = getInt(object, Integer.MIN_VALUE)) ? null : result;
    }
    /********* getInt-end **********/

    /********* getByte-start **********/
    public static Byte getByte(Object object, Byte defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).byteValue();
        }
        try {
            return Byte.parseByte(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static byte getByte(Object object) {
        return getByte(object, (byte) 0);
    }

    /********* getByte-end **********/

    public static Short getShort(Object object, Short defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).shortValue();
        }
        try {
            return Short.parseShort(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getShort(Object object) {
        return getShort(object, (short) 0);
    }

    public static Long getLong(Object object, Long defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getLong(Object object) {
        return getLong(object, 0L);
    }

    public static char getCharacter(Object object, char defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            int intValue = ((Number) object).intValue();
            if (intValue > Character.MAX_VALUE || intValue < Character.MIN_VALUE) {
                return defaultValue;
            }
            return (char) intValue;
        }
        try {
            return Character.valueOf(String.valueOf(object).charAt(0));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getCharacter(Object object) {
        return getCharacter(object, (char) 0);
    }


    public static Double getDouble(Object object, Double defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double getDouble(Object object) {
        return getDouble(object, 0D);
    }

    public static Float getFloat(Object object, Float defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).floatValue();
        }
        try {
            return Float.parseFloat(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float getFloat(Object object) {
        return getFloat(object, 0F);
    }

    public static Boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        String source = String.valueOf(object);
        return isInt(source) ? 0 != getInt(source) : Boolean.parseBoolean(source);
    }

    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    public static boolean isEmpty(Object o) {
        if (null == o) {
            return true;
        } else {
            if (o instanceof String) {
                return ((String) o).isEmpty();
            } else if (o instanceof Collection) {
                return ((Collection<?>) o).isEmpty();
            } else if (o instanceof Map) {
                return ((Map<?, ?>) o).isEmpty();
            } else if (o.getClass().isArray()) {
                return 0 == Array.getLength(o);
            } else if (o instanceof Iterator) {
                return !((Iterator<?>) o).hasNext();
            } else if (o instanceof Enumeration) {
                return !((Enumeration<?>) o).hasMoreElements();
            }
            return false;
        }
    }

    public static int countTrue(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 1 : 0;
        }
        return c;
    }

    public static int countFalse(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 0 : 1;
        }
        return c;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, Object> getMap(Map<String, Object> map, String key) {
        try {
            Object value = map.get(key);
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            } else if (value instanceof List) {
                List list = (List) value;
                if (!list.isEmpty()) {
                    Object element = list.get(0);
                    if (element instanceof Map) {
                        return (Map<String, Object>) element;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean equals(Object o1, Object o2) {
        return equals(o1, o2, false);
    }

    public static boolean equals(Object o1, Object o2, boolean compareLiterally) {
        if (o1 == o2) {
            return true;
        }
        if (null == o1 || null == o2) {
            return false;
        }
        if (compareLiterally) {
            return String.valueOf(o1).equals(String.valueOf(o2));
        } else {
            Class<?> clazz1 = o1.getClass();
            Class<?> clazz2 = o2.getClass();
            if (clazz1.isAssignableFrom(clazz2)) {
                return o1.equals(o2);
            } else if (clazz2.isAssignableFrom(clazz1)) {
                return o2.equals(o1);
            }
        }
        return false;
    }

    public static Properties loadProperties(String filePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        } catch (Exception e) {
            return null;
        } finally {
            IOUtil.close(fis);
        }
    }

    public static Properties loadProperties(InputStream is, boolean close) {
        try {
            Properties prop = new Properties();
            prop.load(is);
            return prop;
        } catch (Exception e) {
            return null;
        } finally {
            if (close) {
                IOUtil.close(is);
            }
        }
    }

    public static void addRequired(Map<String, Object> map, String key, Object value) {
        if (null == value) {
            throw new IllegalArgumentException(key + " is required");
        }
        map.put(key, value);
    }

    public static void addNotBlank(Map<String, Object> map, String key, String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(key + " must be not blank");
        }
        map.put(key, value);
    }

    public static void addNotEmpty(Map<String, Object> map, String key, String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(key + " must be not empty");
        }
        map.put(key, value);
    }

    public static boolean addOptional(Map<String, Object> map, String key, Object value) {
        if (null == value) {
            return false;
        }
        map.put(key, value);
        return true;
    }

    public static BigDecimal getBigDecimal(String value) {
        if (null == value || value.isEmpty()) {
            return BIG_DECIMAL_ZERO;
        }
        return getBigDecimal(value, BIG_DECIMAL_ZERO);
    }

    public static BigDecimal getBigDecimal(BigDecimal value) {
        return getBigDecimal(value, BIG_DECIMAL_ZERO);
    }

    public static BigDecimal getBigDecimal(BigDecimal value, BigDecimal defaultValue) {
        if (null == value)
            return defaultValue;
        return value;
    }

    private static BigDecimal getBigDecimal(String value, BigDecimal defaultValue) {
        BigDecimal decimal;
        try {
            decimal = new BigDecimal(value);
        } catch (Exception e) {
            decimal = defaultValue;
        }
        return decimal;
    }

    public static BigDecimal getDecimal(Object value) {
        return getBigDecimal(getString(value));
    }

    public static BigDecimal getDecimal(Object value, BigDecimal defaultValue) {
        return getBigDecimal(getString(value), defaultValue);
    }

    public static byte[] numberToByteArray(long number, int length) {
        byte[] targets = new byte[length];
        for (int i = length - 1; i >= 0; i--) {
            targets[i] = (byte) (number >> 8 * (length - i - 1) & 0xFF);
        }
        return targets;
    }

    public static <T extends Comparable> boolean inRange(T min, T max, T... ary) {
        if (null != ary && 0 != ary.length) {
            for (T item : ary) {
                if (moreThan(item, min) && lessThan(item, max)) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static boolean moreThan(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) >= 0;
    }

    public static boolean lessThan(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) <= 0;
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String urlEncoder(String source) {
        try {
            return URLEncoder.encode(source, CharsetType.CHARSET_UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static boolean isNotNull(Number val) {
        return null != val;
    }

    public static boolean isNotZero(Number val) {
        return val != null && 0 != val.intValue();
    }

    public static boolean isNotNullAndZero(Number val) {
        return isNotNull(val) && 0 != val.intValue();
    }

    public static Integer getIntByBoolean(Boolean boolValue) {
        return ValueUtil.getBoolean(boolValue, false) ? 1 : 0;
    }

    public static boolean getBooleanByInt(int value) {
        return 0 != ValueUtil.getInt(value, 0);
    }

    public static boolean isNotEmpty(Object obj) {
        return null != obj && !StringUtil.isEmpty(obj.toString());
    }

    /**
     * 非负奇数
     */
    public static boolean isNotNegativeOdd(long number) {
        return number >= 0 && number % 2 == 1;
    }

    /**
     * 非负
     * */
    public static Long getNotNegtiveNum(String number) {
        Long num = ValueUtil.getLong(number , null);
        if( num != null && num < 0L )
            return 0L;
        return num;
    }
}
