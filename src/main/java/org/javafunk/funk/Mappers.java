/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.datastructures.tuples.Quadruple;
import org.javafunk.funk.datastructures.tuples.Triple;
import org.javafunk.funk.functors.Mapper;

import java.util.Iterator;

import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Lazily.rest;
import static org.javafunk.funk.Literals.tuple;

public class Mappers {
    private Mappers() {}

    @SuppressWarnings("unchecked")
    public static <S, T> Mapper<? super Iterable<?>, Pair<S, T>> toPair() {
        return new Mapper<Iterable<?>, Pair<S, T>>() {
            public Pair<S, T> map(Iterable<?> input) {
                return tuple(
                        (S) first(input).get(),
                        (T) first(rest(input)).get());
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <S, T, V> Mapper<? super Iterable<?>, Triple<S, T, V>> toTriple() {
        return new Mapper<Iterable<?>, Triple<S, T, V>>() {
            public Triple<S, T, V> map(Iterable<?> input) {
                return tuple(
                        (S) first(input).get(),
                        (T) first(rest(input)).get(),
                        (V) first(rest(rest(input))).get());
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <S, T, U, V> Mapper<? super Iterable<?>, Quadruple<S, T, U, V>> toQuadruple() {
        return new Mapper<Iterable<?>, Quadruple<S, T, U, V>>() {
            public Quadruple<S, T, U, V> map(Iterable<?> iterable) {
                return tuple(
                        (S) first(iterable).get(),
                        (T) first(rest(iterable)).get(),
                        (U) first(rest(rest(iterable))).get(),
                        (V) first(rest(rest(rest(iterable)))).get());
            }
        };
    }

    static <T> Mapper<? super Iterable<? extends T>, Iterator<? extends T>> toIterators() {
        return new Mapper<Iterable<? extends T>, Iterator<? extends T>>() {
            public Iterator<? extends T> map(Iterable<? extends T> iterable) {
                return iterable.iterator();
            }
        };
    }

    public static <T> Mapper<T, T> identity() {
        return new Mapper<T, T>() {
            @Override public T map(T input) {
                return input;
            }
        };
    }
}
