package com.ncu.springboot.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.ncu.springboot.core.constant.CharsetType;
import com.ncu.springboot.core.jdkextends.NcuStringBuilder;

public abstract class StringUtil {
	public static final char CHINESE_COMMA = '，';
	public static final char ENGLISH_COMMA = ',';
	public static final String TO_STRING_SEPERATOR = "@$";

	public static final String EMPTY_STRING = "";

	public static final String[] EMPTY_STRING_ARR = new String[0];

	public static final String EMPTY_STRING_JSON = "{}";
	public static final String PATERNITY_SEP = "@";
	public static final String HTML_BR = "</br>";

	private static Pattern BLANK_PATTERN = Pattern.compile("\\s");

	private static Pattern UPPER_CASE_PATTERN = Pattern.compile("([A-Z])");

	private static Method propertiesSaveConvertMethod;
	private static Properties dummyProperties;

	static {
		init();
	}

	private static void init() {
		try {
			propertiesSaveConvertMethod = Properties.class.getDeclaredMethod("saveConvert",
					new Class[] { String.class, Boolean.TYPE, Boolean.TYPE });
			propertiesSaveConvertMethod.setAccessible(true);
			dummyProperties = new Properties();
		} catch (Exception e) {
		}
	}

	public static String unicodeToChinese(String unicode) {
		StringBuffer chinese = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int data = Integer.parseInt(hex[i], 16);
			chinese.append((char) data);
		}
		return chinese.toString();
	}

	public static String chineseToUnicode(String chinese) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < chinese.length(); i++) {
			char c = chinese.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}

	public static boolean isEmpty(String str) {
		return null == str || 0 == str.length();
	}

	public static boolean isBlank(String str) {
		if (null == str || 0 == str.length()) {
			return true;
		}
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String nullSafeTrim(String str) {
		return null == str ? null : str.trim();
	}

	public static String emptyToNull(Object value) {
		if (null == value) {
			return null;
		} else {
			String str = String.valueOf(value);
			if (str.isEmpty()) {
				return null;
			} else {
				return str;
			}
		}
	}

	public static String nullToEmpty(Object value) {
		return null == value ? EMPTY_STRING : String.valueOf(value);
	}

	public static String[] splitUsingIndex(String source, String sep) {
		return splitUsingIndex(source, sep, false);
	}

	public static String[] splitUsingIndex(String source, String sep, boolean keepLastEmpty) {
		if (null == source || null == sep || source.isEmpty()) {
			return EMPTY_STRING_ARR;
		}
		List<String> list = new ArrayList<>();
		int sourceLen = source.length();
		int sepLen = sep.length();
		int fromIdx = 0;
		int idx;
		while (-1 != (idx = source.indexOf(sep, fromIdx))) {
			list.add(source.substring(fromIdx, idx));
			fromIdx = idx + sepLen;
			if (fromIdx == sourceLen) {
				break;
			}
		}
		if (fromIdx == sourceLen) {
			if (keepLastEmpty) {
				list.add(EMPTY_STRING);
			}
		} else {
			list.add(source.substring(fromIdx));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String checkHaveNoChineseComma(String source) {
		return StringUtil.replaceAll(new NcuStringBuilder(source), ENGLISH_COMMA, CHINESE_COMMA).toString();
	}

	public static String[] splitUsingIndex(String source, char sep) {
		return splitUsingIndex(source, sep, false);
	}

	public static String[] splitUsingIndex(String source, char sep, boolean keepLastEmpty) {
		if (null == source) {
			return EMPTY_STRING_ARR;
		} else if (source.isEmpty()) {
			return new String[] { EMPTY_STRING };
		}
		List<String> list = new ArrayList<>();
		int sourceLen = source.length();
		int sepLen = 1;
		int fromIdx = 0;
		int idx;
		while (-1 != (idx = source.indexOf(sep, fromIdx))) {
			list.add(source.substring(fromIdx, idx));
			fromIdx = idx + sepLen;
			if (fromIdx == sourceLen) {
				break;
			}
		}
		if (fromIdx == sourceLen) {
			if (keepLastEmpty) {
				list.add(EMPTY_STRING);
			}
		} else {
			list.add(source.substring(fromIdx));
		}
		return list.toArray(new String[list.size()]);
	}

	public static Integer[] splitToInteger(String source, String splitRegex) {
		String[] strArr = StringUtils.split(source, splitRegex);
		int arrLen = strArr.length;
		Integer[] intArr = new Integer[arrLen];
		for (int i = 0; i < arrLen; i++) {
			intArr[i] = ValueUtil.getInt(StringUtils.trim(strArr[i]));
		}
		return intArr;
	}

	public static Long[] splitToLong(String source, String splitRegex) {
		String[] strArr = StringUtils.split(source, splitRegex);
		int arrLen = strArr.length;
		Long[] longArr = new Long[arrLen];
		for (int i = 0; i < arrLen; i++) {
			longArr[i] = ValueUtil.getLong(StringUtils.trim(strArr[i]));
		}
		return longArr;
	}

	public static String toPropertiesValue(String rawString) {
		if (null != propertiesSaveConvertMethod) {
			try {
				return (String) propertiesSaveConvertMethod.invoke(dummyProperties, rawString, false, true);
			} catch (Exception e) {
			}
		}
		return simpleToPropertiesValue(rawString);
	}

	private static String simpleToPropertiesValue(String rawString) {
		if (null != rawString && !rawString.isEmpty()) {
			int len = rawString.length();
			StringBuilder sBuilder = new StringBuilder(len * 2);
			for (int i = 0; i < len; i++) {
				char c = rawString.charAt(i);
				if (0x007e < c) {
					sBuilder.append('\\').append('u').append(Integer.toHexString(c));
				} else {
					sBuilder.append(c);
				}
			}
			return sBuilder.toString();
		} else {
			return rawString;
		}
	}

	public static String camelToUnderlineCase(String source, boolean toUpper) {
		if (null == source) {
			return null;
		}
		String result = camelToSeparateCase(source, '_');
		if (toUpper) {
			result = result.toUpperCase();
		}
		return result;
	}

	public static String camelToUnderlineCase(String source) {
		return camelToUnderlineCase(source, false);
	}

	public static String underlineToCamelCase(String source) {
		return toCamelCase(source, '_');
	}

	public static String toCamelCase(String s, char separator) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		int len = s.length();
		StringBuilder sb = new StringBuilder(len);
		boolean upperCase = false;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c == separator) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String underlineToDot(String s) {
		return s.replaceAll("_", ".");
	}

	public static String camelToSeparateCase(String source, char separator) {
		if (null == source) {
			return null;
		}
		Matcher matcher = UPPER_CASE_PATTERN.matcher(source);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String g = matcher.group();
			matcher.appendReplacement(sb, separator + g.toLowerCase());
		}
		matcher.appendTail(sb);
		if (sb.charAt(0) == separator) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}

	public static String stripAllBlank(String str) {
		return null == str ? null : BLANK_PATTERN.matcher(str).replaceAll("");
	}

	/**
	 * @param @param  str
	 * @param @return
	 * @return boolean
	 * @throws @Title: isNumericAlphaChinese
	 * @Description: 字符串是由数字、字符或中文组成
	 */
	public static boolean isNumericAlphaChinese(String str) {
		if (str.length() == 0)
			return false;
		boolean isNumbercAlphaChines = false;
		for (int i = 0; i < str.length(); i++) {
			isNumbercAlphaChines = false;
			char charAt = str.charAt(i);
			if (Character.isDigit(charAt)) {
				isNumbercAlphaChines = true;
			}
			if (Character.isLetter(charAt)) {
				isNumbercAlphaChines = true;
			}
			if (isChinese(charAt)) {
				isNumbercAlphaChines = true;
			}
			if (!isNumbercAlphaChines) {
				return false;
			}
		}
		return true;

	}

	/**
	 * @param @param  strName
	 * @param @return
	 * @return boolean
	 * @throws @Title: isChinese
	 * @Description: 判断中文汉字和符号
	 */
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param @param  c
	 * @param @return
	 * @return boolean
	 * @throws @Title: isChinese
	 * @Description: 根据Unicode编码完美的判断中文汉字和符号
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static String processPath(String path) {
		if (EnvironmentUtils.isOnWindows()) {
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
		} else {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
		}
		return path;
	}

	public static boolean startWithIgnoreCase(String src, String prefix) {
		src = ValueUtil.getString(src);
		prefix = ValueUtil.getString(prefix);
		return src.toLowerCase().startsWith(prefix.toLowerCase());
	}

	public static boolean containsIgnoreCase(String src, String containStr) {
		src = ValueUtil.getString(src);
		containStr = ValueUtil.getString(containStr);
		return src.toLowerCase().contains(containStr.toLowerCase());
	}

	/**
	 * 字符串的压缩
	 *
	 * @param str 待压缩的字符串
	 * @return 返回压缩后的字符串
	 * @throws IOException
	 */
	public static String compressByGZip(String str, Charset charset) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(out, 2048, false);
			gzip.write(str.getBytes(charset));
			gzip.close();
			return out.toString("ISO-8859-1");
		} catch (IOException e) {
			return str;
		}
	}

	public static String compressByGZip(String str) {
		return compressByGZip(str, CharsetType.CHARSET_UTF_8);
	}

	/**
	 * 字符串的解压
	 *
	 * @param str 对字符串解压
	 * @return 返回解压缩后的字符串
	 * @throws IOException
	 */
	public static String unCompress(String str) throws IOException {
		if (null == str || str.length() <= 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
		GZIPInputStream gzip = new GZIPInputStream(in);
		byte[] buffer = new byte[1024];
		int n;
		while ((n = gzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		return out.toString("UTF-8");
	}

	public static StringBuilder replaceAll(NcuStringBuilder sb, char newChar, char... oldCharAry) {
		return replaceAll(sb.toStringBuilder(), ValueUtil.getString(newChar), CollectionUtil.toStringArray(oldCharAry));
	}

	public static StringBuilder replaceAll(NcuStringBuilder sb, String newStr, String... oldStrAry) {
		return replaceAll(sb.toStringBuilder(), newStr, oldStrAry);
	}

	public static StringBuilder replaceAll(StringBuilder sb, String newStr, String... oldStrAry) {
		if (null != oldStrAry && null != sb && 0 != oldStrAry.length && 0 != sb.length()) {
			for (String oldStr : oldStrAry) {
				int i = sb.indexOf(oldStr);
				int oldLen = oldStr.length();
				int newLen = newStr.length();
				while (i > -1) {
					sb.delete(i, i + oldLen);
					sb.insert(i, newStr);
					i = sb.indexOf(oldStr, i + newLen);
				}
				return sb;
			}
		}
		return sb;
	}

	public static String toUpperInIndex(String source, int index) {
		if (isEmpty(source) || index <= 0 || index > source.length()) {
			return source;
		}
		NcuStringBuilder builder = NcuStringBuilder.getInstance();
		builder.append(source.substring(0, index - 1), ValueUtil.getString(source.charAt(index - 1)).toUpperCase(),
				source.substring(index));
		return builder.toString();
	}

	public static String toLowerInIndex(String source, int index) {
		if (isEmpty(source) || index <= 0 || index > source.length()) {
			return source;
		}
		NcuStringBuilder builder = NcuStringBuilder.getInstance();
		builder.append(source.substring(0, index - 1), ValueUtil.getString(source.charAt(index - 1)).toLowerCase(),
				source.substring(index));
		return builder.toString();
	}

	public static String transMethodNameFromUnderline(String source) {
		if (source.contains("_")) {
			String[] sourceAry = StringUtils.split(source, "_");
			NcuStringBuilder builder = NcuStringBuilder.getInstance();
			for (int i = 0; i < sourceAry.length; i++) {
				if (i == 0) {
					builder.append(sourceAry[i]);
				} else {
					builder.append(toUpperInIndex(sourceAry[i], 1));
				}
			}
			return builder.toString();
		}
		return source;
	}

	public static List<Long> findParentMenuIdList(Map<Long, String> id2paternityMap) {
		if (CollectionUtil.isEmpty(id2paternityMap)) {
			return new ArrayList<>();
		}
		Map<String, List<Long>> paternity2idMap = new HashMap<>();
		for (Map.Entry<Long, String> entry : id2paternityMap.entrySet()) {
			CollectionUtil.addItemToMapList(paternity2idMap, entry.getValue(), entry.getKey());
		}
		Set<String> parentPaternitySet = new HashSet<>(paternity2idMap.size());
		for (String paternity : id2paternityMap.values()) {
			if (CollectionUtil.existStartWith(parentPaternitySet, paternity, true)) {
				parentPaternitySet.add(paternity);
			}
		}
		List<Long> resultList = new ArrayList<>();
		for (String parentPaternity : parentPaternitySet) {
			resultList.addAll(paternity2idMap.get(parentPaternity));
		}
		return resultList;
	}

	public static String formatContent(String source, Object... vars) {
		source = ValueUtil.getString(source);
		if (StringUtil.isEmpty(source)) {
			return source;
		}
		return new MessageFormat(source).format(vars);
	}

	public static int calcOccurrence(String source, char w) {
		int count = 0;
		for (char c : source.toCharArray()) {
			if (c == w) {
				count++;
			}
		}
		return count;
	}
}
