package org.smallvaluesofcool.misc;

import org.smallvaluesofcool.misc.functional.functors.Factory;

import java.util.Map;

public class MapUtils {
    public static <U, V> V getOrAddDefault(Map<U, V> map, U key, DefaultValueFactory<V> factory) {
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
