package com.ncu.springboot.core.util;

import org.apache.commons.lang3.StringUtils;

public class PrintUtil {

    public static String printSensitiveRealLength(String info) {
        if (StringUtils.isEmpty(info)) {
            return "";
        } else {
            return info.replaceAll(".", "*");
        }
    }

    public static String printSensitiveFixLength(String info) {
        if (StringUtils.isEmpty(info)) {
            return "";
        } else {
            return "******";
        }
    }

    public static String printSensitiveKeepTerminal(String info) {
        return printSensitiveKeepTerminal(info, 50);
    }

    public static String printSensitiveKeepTerminal(String info, int maskPercentage) {
        if (StringUtils.isEmpty(info)) {
            return "";
        } else {
            if (maskPercentage > 100) {
                maskPercentage = 100;
            } else if (maskPercentage < 0) {
                maskPercentage = 0;
            }
            int fullLength = info.length();
            int maskLength = fullLength * maskPercentage / 100;
            if (0 == maskLength && maskPercentage > 0) {
                maskLength = 1;
            }
            int plainLength = fullLength - maskLength;
            int plainHalfLength = plainLength / 2;
            if (0 == plainHalfLength && maskPercentage < 100) {
                plainHalfLength = 1;
            }
            int maskStart = plainHalfLength;
            int maskEnd = maskStart + maskLength;
            if (maskEnd > fullLength) {
                maskEnd = fullLength;
            }
            StringBuilder sBuilder = new StringBuilder(info);
            for (int i = maskStart; i < maskEnd; i++) {
                sBuilder.setCharAt(i, '*');
            }
            return sBuilder.toString();
        }
    }

    public static String printBigDataLength(String data) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("(length=");
        if (null == data) {
            sBuilder.append(0);
        } else {
            sBuilder.append(data.length());
        }
        sBuilder.append(')');
        return sBuilder.toString();
    }

    public static String printPwd(String pwd) {
        return printSensitiveFixLength(pwd);
    }

    public static String printIdNo(String idNo) {
        return printSensitiveKeepTerminal(idNo);
    }

    public static String printBankCardNo(String bankCardNo) {
        return printSensitiveKeepTerminal(bankCardNo);
    }

}
