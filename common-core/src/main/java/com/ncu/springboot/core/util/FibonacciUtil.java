package com.ncu.springboot.core.util;

/**
 * 斐波那契数列
 */
public class FibonacciUtil {
    private static final int DEFAULT_FIRST = 1;
    private static final int DEFAULT_SECOND = 1;

    public static int calcFibonacci(int times) {
        return calcFibonacci(times, DEFAULT_FIRST, DEFAULT_SECOND);
    }

    public static int calcFibonacci(int times, int first, int second) {
        if (0 >= times) {
            return 0;
        }
        if (0 == first && 0 == second) {
            return 0;
        }
        return 1 == times ? first : 2 == times ? second : calcFibonacciInternal(times, first, second);
    }

    private static int calcFibonacciInternal(int times, int first, int second) {
        if (1 == times) {
            return first;
        } else if (2 == times) {
            return second;
        }
        return calcFibonacciInternal(--times, second, first + second);
    }
}
