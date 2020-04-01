package com.ncu.springboot.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ncu.springboot.core.constant.CharsetType;

public class RequestUtil {


    public static String getRequestBody(HttpServletRequest request) {
        try {
            return IOUtil.readReader(request.getReader());
        } catch (Exception e) {
            return "";
        }
    }

    public static Map<String, String> getParameterMapByBody(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<>();
        String body = getRequestBody(request);
        String[] parameterAry = StringUtils.split(body, "&");
        Arrays.stream(parameterAry).forEach( parameter -> {
            int index = parameter.indexOf("=");
            if (-1 != index) {
                parameterMap.put(parameter.substring(0, index), urlDecoder(parameter.substring(index + 1)));
            }
        });
        return parameterMap;
    }

    public static String urlDecoder(String content) {
        return urlDecoder(content, CharsetType.CHARSET_UTF_8.name());
    }

    public static String urlDecoder(String content, String encode) {
        try {
            return URLDecoder.decode(content, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }

    public static String encode(String content, String encode) {
        try {
            return URLEncoder.encode(content, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }

    public static String encode(String content) {
        return encode(content, "utf-8");
    }

    public static void setNoCacheResponse(HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
    }

    public static final Map<String, Object> getSingleParameterMap(HttpServletRequest request, String separator) {
        Map<String, Object> returnMap = new HashMap<>();
        request.getParameterMap().forEach( (key , paramValues) -> {
            String paramValue = CollectionUtil.isEmpty(paramValues) ? "" : StringUtils.join(paramValues, separator);
            returnMap.put(key, paramValue);
        } );
        return returnMap;
    }
}
