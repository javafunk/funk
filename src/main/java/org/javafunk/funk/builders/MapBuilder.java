/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.builders;

import org.javafunk.funk.Classes;
import org.javafunk.funk.Tuples;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.functions.UnaryFunction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.javafunk.funk.Lazily.map;
import static org.javafunk.funk.Literals.iterableFrom;
import static org.javafunk.funk.Literals.mapEntryFor;

public class MapBuilder<K, V>
        extends AbstractBuilder<Map.Entry<K, V>, MapBuilder<K, V>, Map<K, V>>
        implements AbstractBuilder.WithCustomImplementationSupport<Map.Entry<K, V>, Map, Map<K, V>> {
    private Map<K, V> elements = new HashMap<K, V>();

    public static <K, V> MapBuilder<K, V> mapBuilder() {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapBuilder(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>();
    }

    @Override public Map<K, V> build() {
        return new HashMap<K, V>(elements);
    }

    @Override public Map<K, V> build(Class<? extends Map> implementationClass) {
        @SuppressWarnings("unchecked")
        Map<K, V> map = (Map<K, V>) Classes.uncheckedInstantiate(implementationClass);
        map.putAll(elements);
        return map;
    }

    @Override public Map<K, V> build(UnaryFunction<? super Iterable<Map.Entry<K, V>>, ? extends Map<K, V>> builderFunction) {
        return builderFunction.call(Collections.unmodifiableSet(elements.entrySet()));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V>[] pairs) {
        return andPairs(iterableFrom(pairs));
    }

    public MapBuilder<K, V> andPairs(Iterable<? extends Pair<K, V>> pairs) {
        return and(map(pairs, Tuples.<K, V>toMapEntry()));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V>[] pairs) {
        return andPairs(iterableFrom(pairs));
    }

    public MapBuilder<K, V> withPairs(Iterable<? extends Pair<K, V>> pairs) {
        return and(map(pairs, Tuples.<K, V>toMapEntry()));
    }

    @Override protected void handle(Map.Entry<K, V> element) {
        elements.put(element.getKey(), element.getValue());
    }

    @Override protected MapBuilder<K, V> updatedBuilder() {
        return this;
    }

    public MapBuilder<K, V> withKeyValuePair(K k1, V v1) {
        return and(mapEntryFor(k1, v1));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9));
    }

    public MapBuilder<K, V> withKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9), mapEntryFor(k10, v10));
    }

    public MapBuilder<K, V> andKeyValuePair(K k1, V v1) {
        return and(mapEntryFor(k1, v1));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9));
    }

    public MapBuilder<K, V> andKeyValuePairs(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return and(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9), mapEntryFor(k10, v10));
    }

    public MapBuilder<K, V> withPair(Pair<K, V> p1) {
        return and(mapEntryFor(p1));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2) {
        return and(mapEntryFor(p1), mapEntryFor(p2));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8, Pair<K, V> p9) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8), mapEntryFor(p9));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8, Pair<K, V> p9, Pair<K, V> p10) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8), mapEntryFor(p9), mapEntryFor(p10));
    }

    public MapBuilder<K, V> withPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8, Pair<K, V> p9, Pair<K, V> p10, Pair<K, V>... p11on) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8), mapEntryFor(p9), mapEntryFor(p10))
                .andPairs(p11on);
    }

    public MapBuilder<K, V> andPair(Pair<K, V> p1) {
        return and(mapEntryFor(p1));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2) {
        return and(mapEntryFor(p1), mapEntryFor(p2));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8, Pair<K, V> p9) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8), mapEntryFor(p9));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8, Pair<K, V> p9, Pair<K, V> p10) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8), mapEntryFor(p9), mapEntryFor(p10));
    }

    public MapBuilder<K, V> andPairs(Pair<K, V> p1, Pair<K, V> p2, Pair<K, V> p3, Pair<K, V> p4, Pair<K, V> p5, Pair<K, V> p6, Pair<K, V> p7, Pair<K, V> p8, Pair<K, V> p9, Pair<K, V> p10, Pair<K, V>... p11on) {
        return and(mapEntryFor(p1), mapEntryFor(p2), mapEntryFor(p3), mapEntryFor(p4), mapEntryFor(p5), mapEntryFor(p6), mapEntryFor(p7), mapEntryFor(p8), mapEntryFor(p9), mapEntryFor(p10))
                .andPairs(p11on);
    }
}
