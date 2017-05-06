package com.base.platform.utils.java;

import java.util.HashMap;
import java.util.Map;

/**
 * 关于map的操作
 */
public class MapBuilder<K, V> {
    private Map<K, V> map;

    public MapBuilder() {
        map = new HashMap<K, V>();
    }

    public MapBuilder<K, V> add(K key, V value) {
        map.put(key, value);
        return this;
    }

    public Map<K, V> getUnmodifiableMap() {
//        return Collections.unmodifiableMap(map);
        return map;
    }

    public Map<K, V> getMap() {
        return map;
    }

}
