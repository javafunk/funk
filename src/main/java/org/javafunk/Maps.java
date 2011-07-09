package org.javafunk;

import org.javafunk.functional.functors.Factory;

import java.util.Map;

public class Maps {
    public static <U, V> V getOrAddDefault(Map<U, V> map, U key, DefaultValueFactory<? extends V> factory) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            V newValue = factory.create();
            map.put(key, newValue);
            return newValue;
        }
    }

    public interface DefaultValueFactory<T> extends Factory<T> {
        T create();
    }
}
