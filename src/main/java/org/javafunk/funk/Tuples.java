/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.behaviours.ordinals.First;
import org.javafunk.funk.behaviours.ordinals.Second;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Mapper;

import java.util.Map;

import static org.javafunk.funk.Literals.mapEntryFor;

public class Tuples {
    public static <K, V> Mapper<Pair<K, V>, Map.Entry<K, V>> toMapEntry() {
        return new Mapper<Pair<K, V>, Map.Entry<K, V>>() {
            @Override public Map.Entry<K, V> map(Pair<K, V> pair) {
                return mapEntryFor(pair);
            }
        };
    }

    public static <T> Iterable<T> firsts(Iterable<? extends First<T>> firstables) {
        return Lazily.map(firstables, Tuples.<T>toFirst());
    }

    public static <T> Iterable<T> seconds(Iterable<? extends Second<T>> secondables) {
        return Lazily.map(secondables, Tuples.<T>toSecond());
    }

    public static <T> Mapper<? super First<T>, T> toFirst() {
        return new Mapper<First<T>, T>() {
            @Override public T map(First<T> firstable) {
                return firstable.getFirst();
            }
        };
    }

    public static <T> Mapper<? super Second<T>, T> toSecond() {
        return new Mapper<Second<T>, T>() {
            @Override public T map(Second<T> secondable) {
                return secondable.getSecond();
            }
        };
    }
}
