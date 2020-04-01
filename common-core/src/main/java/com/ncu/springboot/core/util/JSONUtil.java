package com.ncu.springboot.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Map.Entry;

public abstract class JSONUtil {

    public static final String EMPTY_JSON_OBJECT_STRING = "{}";

    public static JSONObject merge(JSONObject... jsonObjects) {
        int length;
        if (null == jsonObjects || 0 == (length = jsonObjects.length)) {
            return new JSONObject();
        }
        JSONObject base = jsonObjects[0];
        for (int i = 1; i < length; i++) {
            JSONObject other = jsonObjects[i];
            if (null == other) {
                continue;
            }
            for (Entry<String, Object> entry : other.entrySet()) {
                base.put(entry.getKey(), entry.getValue());
            }
        }
        return base;
    }

    public static JSONObject merge(JSONObject base, String key, Object value) {
        if (null == base) {
            base = new JSONObject();
        }
        if (null != key) {
            base.put(key, value);
        }
        return base;
    }

    public static JSONObject merge(JSONObject base, Map<String, Object> map) {
        if (null == base) {
            base = new JSONObject();
        }
        if ( !CollectionUtil.isEmpty(map) ) {
            for (Entry<String, Object> entry : map.entrySet()) {
                base.put(entry.getKey(), entry.getValue());
            }
        }
        return base;
    }

    public static boolean equals(JSONObject o, JSONObject ao) {
        return o == ao || null != o && o.equals(ao);
    }

    public static boolean equals(JSONArray o, JSONArray ao) {
        return o == ao || null != o && o.equals(ao);
    }

    public static boolean equals(JSONObject o, JSONObject ao, String[] keys) {
        if (o == ao || CollectionUtil.isEmpty(keys)) {
            return true;
        }
        if (null == o) {
            return false;
        }
        for (String key : keys) {
            Object ov = o.get(key);
            Object aov = ao.get(key);
            if (null == ov && null != aov) {
                return false;
            } else {
                if (null == aov) {
                    return false;
                } else if (!ov.equals(aov)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static JSONObject clone(JSONObject o) {
        return null == o ? null : (JSONObject) o.clone();
    }

    public static JSONArray clone(JSONArray o) {
        return null == o ? null : (JSONArray) o.clone();
    }

}
