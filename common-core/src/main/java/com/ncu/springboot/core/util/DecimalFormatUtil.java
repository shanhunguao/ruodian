package com.ncu.springboot.core.util;

import java.text.DecimalFormat;

public class DecimalFormatUtil {

    // number format

    public static String formatDecimalFix2(double amount) {
        return new DecimalFormat("0.00").format(amount);
    }

    public static String formatDecimalFix3(double amount) {
        return new DecimalFormat("0.000").format(amount);
    }

    public static String formatPercentageMax0(double ratio) {
        return new DecimalFormat("0%").format(ratio);
    }

    public static String formatPercentageMax2(double ratio) {
        return new DecimalFormat("0.##%").format(ratio);
    }

    public static String formatPercentageFix2(double ratio) {
        return new DecimalFormat("0.00%").format(ratio);
    }

    public static String formatPercentageFix3(double ratio) {
        return new DecimalFormat("0.000%").format(ratio);
    }

    public static String formatPermillageMax1(double ratio) {
        return new DecimalFormat("0.#‰").format(ratio);
    }

    public static String formatPermillageMax2(double ratio) {
        return new DecimalFormat("0.##‰").format(ratio);
    }

    public static String formatPermillageMax6(double ratio) {
        return new DecimalFormat("0.######‰").format(ratio);
    }

}
