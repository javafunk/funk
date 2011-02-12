package org.smallvaluesofcool.misc;

import org.smallvaluesofcool.misc.collections.DefaultFactory;

import java.util.Map;

public class MapUtils {
    public static <S, T> T getOrAddDefault(Map<S, T> map, S key, DefaultFactory<T> factory) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            T newValue = factory.create();
            map.put(key, newValue);
            return newValue;
        }
    }
}
