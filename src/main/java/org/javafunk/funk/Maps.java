/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Factory;

import java.util.Map;

public class Maps {
    private Maps() {}
    
    public static <U, V> V getOrAddDefault(Map<U, V> map, U key, DefaultValueFactory<? extends V> factory) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            V newValue = factory.create();
            map.put(key, newValue);
            return newValue;
        }
    }

    public interface DefaultValueFactory<T> extends Factory<T> {}
}
