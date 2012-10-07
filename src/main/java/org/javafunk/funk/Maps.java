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
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.functors.procedures.UnaryProcedure;

import java.util.Map;

import static org.javafunk.funk.functors.adapters.FactoryNullaryFunctionAdapter.factoryNullaryFunction;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

public class Maps {
    private Maps() {}

    public static <U, V> V getOrAdd(Map<U, V> map, U key, Mapper<? super U, ? extends V> mapper) {
        return getOrAdd(map, key, mapperUnaryFunction(mapper));
    }

    public static <U, V> V getOrAdd(Map<U, V> map, U key, Factory<? extends V> factory) {
        return getOrAdd(map, key, factoryNullaryFunction(factory));
    }

    public static <U, V> V getOrAdd(Map<U, V> map, U key, UnaryFunction<? super U, ? extends V> mapper) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            V newValue = mapper.call(key);
            map.put(key, newValue);
            return newValue;
        }
    }

    public static <U, V> V getOrAdd(Map<U, V> map, U key, final NullaryFunction<? extends V> factory) {
        return getOrAdd(map, key, new Mapper<U, V>() {
            @Override public V map(U input) {
                return factory.call();
            }
        });
    }
}
