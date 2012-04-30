/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder<K, V> extends LinkedHashMap<K, V> {
    public MapBuilder<K, V> with(K key, V value) {
        return and(key, value);
    }
    public MapBuilder<K, V> and(K key, V value) {
        put(key, value);
        return this;
    }

    public Map<K, V> build() {
        return this;
    }
}
