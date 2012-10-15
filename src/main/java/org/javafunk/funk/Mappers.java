/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.*;
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
    public static <S, T, V> Mapper<? super Iterable<?>, Triple<S, T, V>> toTriple() {
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
    public static <S, T, U, V> Mapper<? super Iterable<?>, Quadruple<S, T, U, V>> toQuadruple() {
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

    @SuppressWarnings("unchecked")
    public static <R, S, T, U, V> Mapper<? super Iterable<?>, Quintuple<R, S, T, U, V>> toQuintuple() {
        return new Mapper<Iterable<?>, Quintuple<R, S, T, U, V>>() {
            @Override public Quintuple<R, S, T, U, V> map(Iterable<?> iterable) {
                return tuple(
                        (R) first(iterable).get(),
                        (S) first(rest(iterable)).get(),
                        (T) first(rest(rest(iterable))).get(),
                        (U) first(rest(rest(rest(iterable)))).get(),
                        (V) first(rest(rest(rest(rest(iterable))))).get());
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <R, S, T, U, V, W> Mapper<? super Iterable<?>, Sextuple<R, S, T, U, V, W>> toSextuple() {
        return new Mapper<Iterable<?>, Sextuple<R, S, T, U, V, W>>() {
            @Override public Sextuple<R, S, T, U, V, W> map(Iterable<?> iterable) {
                return tuple(
                        (R) first(iterable).get(),
                        (S) first(rest(iterable)).get(),
                        (T) first(rest(rest(iterable))).get(),
                        (U) first(rest(rest(rest(iterable)))).get(),
                        (V) first(rest(rest(rest(rest(iterable))))).get(),
                        (W) first(rest(rest(rest(rest(rest(iterable)))))).get());
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <R, S, T, U, V, W, X> Mapper<? super Iterable<?>, Septuple<R, S, T, U, V, W, X>> toSeptuple() {
        return new Mapper<Iterable<?>, Septuple<R, S, T, U, V, W, X>>() {
            @Override public Septuple<R, S, T, U, V, W, X> map(Iterable<?> iterable) {
                return tuple(
                        (R) first(iterable).get(),
                        (S) first(rest(iterable)).get(),
                        (T) first(rest(rest(iterable))).get(),
                        (U) first(rest(rest(rest(iterable)))).get(),
                        (V) first(rest(rest(rest(rest(iterable))))).get(),
                        (W) first(rest(rest(rest(rest(rest(iterable)))))).get(),
                        (X) first(rest(rest(rest(rest(rest(rest(iterable))))))).get());
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <R, S, T, U, V, W, X, Y> Mapper<? super Iterable<?>, Octuple<R, S, T, U, V, W, X, Y>> toOctuple() {
        return new Mapper<Iterable<?>, Octuple<R, S, T, U, V, W, X, Y>>() {
            @Override public Octuple<R, S, T, U, V, W, X, Y> map(Iterable<?> iterable) {
                return tuple(
                        (R) first(iterable).get(),
                        (S) first(rest(iterable)).get(),
                        (T) first(rest(rest(iterable))).get(),
                        (U) first(rest(rest(rest(iterable)))).get(),
                        (V) first(rest(rest(rest(rest(iterable))))).get(),
                        (W) first(rest(rest(rest(rest(rest(iterable)))))).get(),
                        (X) first(rest(rest(rest(rest(rest(rest(iterable))))))).get(),
                        (Y) first(rest(rest(rest(rest(rest(rest(rest(iterable)))))))).get());
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <R, S, T, U, V, W, X, Y, Z> Mapper<? super Iterable<?>, Nonuple<R, S, T, U, V, W, X, Y, Z>> toNonuple() {
        return new Mapper<Iterable<?>, Nonuple<R, S, T, U, V, W, X, Y, Z>>() {
            @Override public Nonuple<R, S, T, U, V, W, X, Y, Z> map(Iterable<?> iterable) {
                return tuple(
                        (R) first(iterable).get(),
                        (S) first(rest(iterable)).get(),
                        (T) first(rest(rest(iterable))).get(),
                        (U) first(rest(rest(rest(iterable)))).get(),
                        (V) first(rest(rest(rest(rest(iterable))))).get(),
                        (W) first(rest(rest(rest(rest(rest(iterable)))))).get(),
                        (X) first(rest(rest(rest(rest(rest(rest(iterable))))))).get(),
                        (Y) first(rest(rest(rest(rest(rest(rest(rest(iterable)))))))).get(),
                        (Z) first(rest(rest(rest(rest(rest(rest(rest(rest(iterable))))))))).get());
            }
        };
    }

    public static <T> Mapper<? super Iterable<? extends T>, Iterator<? extends T>> toIterators() {
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
