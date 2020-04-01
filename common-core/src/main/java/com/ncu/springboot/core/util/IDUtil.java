package com.ncu.springboot.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class IDUtil {


    /**
     * 全限定名分隔符。
     */
    public static final char FULL_ID_DELIMITER = '_';

    private static final String EMPTY = "";


    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String genUUIDAddPrefix(String prefix) {
        String uuid = genUUID();
        if (StringUtils.isNotBlank(prefix)) {
            uuid = prefix + uuid;
        }
        return uuid;
    }


    /**
     * 生成全限定名。
     *
     * @param ids 按顺序的一组ID。
     * @return 生成的全限定名。
     */
    public static String genFullID(String... ids) {
        return buildIDChain(true, ids);
    }

    private static String buildIDChain(boolean noTailDelimiter, String... ids) {
        if (null != ids && 0 < ids.length) {
            StringBuilder sBuilder = new StringBuilder();
            for (String id : ids) {
                sBuilder.append(id).append(FULL_ID_DELIMITER);
            }
            if (noTailDelimiter) {
                sBuilder.deleteCharAt(sBuilder.length() - 1);
            }
            return sBuilder.toString();
        } else {
            return EMPTY;
        }
    }

}
