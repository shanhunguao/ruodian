package com.ncu.springboot.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * @param @param  mobiles
     * @param @return
     * @return boolean
     * @throws
     * @Title: isMobileNO
     * @Description: 验证是否手机号码
     * 移动:
     * 2G号段(GSM网络)有139,138,137,136,135,134(0-8),159,158,152,151,150
     * 3G号段(TD-SCDMA网络)有157,188,187
     * 147是移动TD上网卡专用号段.
     * 联通:
     * 2G号段(GSM网络)有130,131,132,155,156
     * 3G号段(WCDMA网络)有186,185
     * 电信:
     * 2G号段(CDMA网络)有133,153
     * 3G号段(CDMA网络)有189,180
     */
    public static boolean isMobileNO(String mobiles) {
        String str = "^((13[0-9])|(15[^4,\\D])|(18[0-2,5-9]))\\d{8}$";
        return commonRegex(mobiles, str);
    }

    /**
     * @param @param  email
     * @param @return
     * @return boolean
     * @throws
     * @Title: isEmail
     * @Description: 验证邮箱格式
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        return commonRegex(email, str);
    }

    /**
     * @param @param  ipAddress
     * @param @return
     * @return boolean
     * @throws
     * @Title: isIPAddress
     * @Description:IP地址是否正确验证
     */
    public static boolean isIPAddress(String ipAddress) {
        String str = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
        return commonRegex(ipAddress, str);
    }

    /**
     * @param @param  checkStr
     * @param @param  rule
     * @param @return
     * @return boolean
     * @throws
     * @Title: commonRegex
     * @Description: 公用验证方法
     */
    public static boolean commonRegex(String checkStr, String rule) {
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(checkStr);
        return m.matches();
    }

    /**
     * 提取数字
     *
     * @param src   源字符串
     * @param digit 位数 如果小于1,或者为null,则获取所有
     * @param
     */
    public static List<Integer> extractNumber(String src, Integer digit) {
        String regex;
        if (null == digit || digit < 1) {
            regex = "(\\d+)";
        } else {
            regex = String.format("(\\d{%d}+)", digit);
        }
        List<Integer> numberList = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(src);
        while (m.find()) {
            numberList.add(Integer.valueOf(m.group(1)));
        }
        return numberList;
    }

    public static Set<String> extractStringWithNoRepeat(String src, Integer digit) {
        String regex = "";
        if (null == digit || digit < 1) {
            regex = "(\\w+)";
        } else {
            regex = String.format("(\\w{%d}+)", digit);
        }
        Set<String> resultList = new HashSet<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(src);
        while (m.find()) {
            resultList.add(m.group(1));
        }
        return resultList;
    }
}
