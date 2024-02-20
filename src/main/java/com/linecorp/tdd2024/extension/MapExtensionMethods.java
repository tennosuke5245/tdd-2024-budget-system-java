package com.linecorp.tdd2024.extension;

import java.util.Map;
import java.util.Optional;

public class MapExtensionMethods {

    public static <K, V> Optional<V> getAndRemove(Map<K, V> obj, K key) {
        V value = obj.get(key);
        obj.remove(key);
        return Optional.ofNullable(value);
    }

}
