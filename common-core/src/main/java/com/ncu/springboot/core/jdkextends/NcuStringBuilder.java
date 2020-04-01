package com.ncu.springboot.core.jdkextends;

import org.apache.commons.lang3.StringUtils;

public class NcuStringBuilder {
    private StringBuilder builder;

    private NcuStringBuilder() {
        builder = new StringBuilder();
    }

    public NcuStringBuilder(String content) {
        builder = new StringBuilder(content);
    }

    public static NcuStringBuilder getInstance() {
        return new NcuStringBuilder();
    }

    public NcuStringBuilder clear() {
        builder.setLength(0);
        return this;
    }

    public NcuStringBuilder append(Object source) {
        builder.append(source);
        return this;
    }

    public NcuStringBuilder append(Object... sourceAry) {
        if (null != sourceAry && 0 != sourceAry.length) {
            for (Object source : sourceAry) {
                builder.append(source);
            }
        }
        return this;
    }

    public NcuStringBuilder fromString(String source) {
        builder.setLength(0);
        return append(source);
    }

    public StringBuilder toStringBuilder() {
        return builder;
    }

    public String toString() {
        return builder.toString();
    }

    public int len() {
        return builder.length();
    }

    public NcuStringBuilder subStringWithBuilder(int start, int end) {
        return fromString(builder.substring(start, end));
    }

    public String subString(int start, int end) {
        return builder.substring(start, end);
    }

    public int indexOf(String str) {
        return builder.indexOf(str);
    }

    public char charAt(int idx) {
        return builder.charAt(idx);
    }

    public NcuStringBuilder deleteLast() {
        int len = len();
        return len > 0 ? deleteCharAt(len - 1) : this;
    }

    public NcuStringBuilder deleteCharAt(int idx) {
        builder.deleteCharAt(idx);
        return this;
    }

    public NcuStringBuilder delete(int start, int end) {
        builder.delete(start, end);
        return this;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(builder.toString());
    }

    public boolean isBlank() {
        return StringUtils.isBlank(builder.toString());
    }

}
