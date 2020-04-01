package com.ncu.springboot.core.util;

import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomUtil {

    private static final String NUMERIC = "0123456789";

    private static final String LOWERCASE_ALPHA = "abcdefghijklmnopqrstuvwxyz";

    private static final String UPPERCASE_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXWZ";

    private static final String ALPHA = LOWERCASE_ALPHA + UPPERCASE_ALPHA;

    private static final String NUMERIC_ALPHA = NUMERIC + ALPHA;

    public static String genRandomString(int length) {
        return genNumericAlphaRandomString(length);
    }

    public static String genNumericRandomString(int length) {
        return genRandomString(NUMERIC, length);
    }

    public static String genLowerAlphaRandomString(int length) {
        return genRandomString(LOWERCASE_ALPHA, length);
    }

    public static String genUpperAlphaRandomString(int length) {
        return genRandomString(UPPERCASE_ALPHA, length);
    }

    public static String genAlphaRandomString(int length) {
        return genRandomString(ALPHA, length);
    }

    public static String genNumericAlphaRandomString(int length) {
        return genRandomString(NUMERIC_ALPHA, length);
    }

    public static String genRandomString(String dict, int length) {
        return genRandomString(dict, length, null);
    }

    public static String genRandomString(String dict, int length, Random random) {
        int dictLength = dict.length();
        if (null == random) {
            random = new Random();
        }
        StringBuilder sBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = dict.charAt(random.nextInt(dictLength));
            sBuilder.append(c);
        }
        return sBuilder.toString();
    }

    public static String genSecureRandomString(String dict, int length) {
        return genRandomString(dict, length, new SecureRandom());
    }

    public static String genNumericAlphaSecureRandomString(int length) {
        return genSecureRandomString(NUMERIC_ALPHA, length);
    }

    public static <T> T randomItem(List<T> list) {
        Assert.isTrue(!CollectionUtil.isEmpty(list), "random heap is nulll or empty , please check");
        Random random = new Random();
        int radix = 100, randomRadix = list.size() * radix;
        return list.get((int) (random.nextFloat() * randomRadix / radix));
    }

    public static <T> T randomItem(T... ary) {
        Assert.isTrue(!CollectionUtil.isEmpty(ary), "random heap is nulll or empty , please check");
        Random random = new Random();
        int radix = 100, randomRadix = ary.length * radix;
        return ary[(int) (random.nextFloat() * randomRadix / radix)];
    }

    public static <T> List<T> randomItemInBatch(int batchSize, T... array) {
        return randomItemInBatch(batchSize , Arrays.asList(array));
    }

    public static <T> List<T> randomItemInBatch(int batchSize, List<T> randomList) {
        List<T> resultList = new ArrayList<>();
        if (CollectionUtil.isEmpty(randomList)) {
            return resultList;
        }
        Random random = new Random();
        int radix = 100, randomRadix = randomList.size() * radix;
        for (int i = 0; i < batchSize; i++) {
            resultList.add( randomList.get((int) (random.nextFloat() * randomRadix / radix)) );
        }
        return resultList;
    }

    public static <T> T[] randomItemInBatch(Class<T> clazz, int batchSize, T... randomAry) {
        T[] resultAry = ReflectionUtil.newArray(clazz, batchSize);
        if (CollectionUtil.isEmpty(randomAry)) {
            return resultAry;
        }
        Random random = new Random();
        int radix = 100, randomRadix = randomAry.length * radix;
        for (int i = 0; i < batchSize; i++) {
            resultAry[i] = randomAry[(int) (random.nextFloat() * randomRadix / radix)];
        }
        return resultAry;
    }
}
