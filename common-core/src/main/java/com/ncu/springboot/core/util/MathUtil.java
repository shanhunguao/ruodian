package com.ncu.springboot.core.util;

import java.util.Collection;

public class MathUtil {

    public static final int max(int... ary) {
        Integer max = Integer.MIN_VALUE;
        for (Integer item : ary) {
            max = Math.max(item, max);
        }
        return max;
    }

    public static final int min(Integer... ary) {
        Integer min = Integer.MAX_VALUE;
        for (Integer item : ary) {
            min = Math.min(item, min);
        }
        return min;
    }

    public static final int min(Collection<Integer> collection) {
        Integer min = Integer.MAX_VALUE;
        for (Integer item : collection) {
            min = Math.min(item, min);
        }
        return min;
    }

    public static int sum(Collection<String> collection) {
        int sum = 0;
        for (String val : collection) {
            sum += ValueUtil.getInt(val);
        }
        return sum;
    }
}
