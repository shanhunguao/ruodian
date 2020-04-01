package com.ncu.springboot.core.jdkextends;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
    private Map<K, V> map;

    private MapBuilder() {
    }

    public static MapBuilder getInstance() {
        return new MapBuilder<>();
    }

    public static <K, V> MapBuilder<K, V> getInstance(Class<K> keyClazz, Class<V> valueClazz) {
        return new MapBuilder<>();
    }

    public MapBuilder resetMap() {
        map = new HashMap<>();
        return this;
    }

    public MapBuilder genMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return this;
    }

    public MapBuilder fromMap(Map<K, V> map) {
        this.map = map;
        return this;
    }

    public MapBuilder put(K key, V value) {
        toMap().put(key, value);
        return this;
    }

    public Map<K, V> toMap() {
        genMap();
        return map;
    }

    public MapBuilder putAll(Map<K, V> map) {
        toMap().putAll(map);
        return this;
    }

}
