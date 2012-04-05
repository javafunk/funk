package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.datastructures.tuples.Quadruple;
import org.javafunk.funk.datastructures.tuples.Triple;
import org.javafunk.funk.functors.Mapper;

import java.util.Iterator;

import static org.javafunk.funk.Eager.first;
import static org.javafunk.funk.Lazy.rest;
import static org.javafunk.funk.Literals.tuple;

class Mappers {
    @SuppressWarnings("unchecked")
    public static <S, T> Mapper<? super Iterable<?>, Pair<S, T>> toPair() {
        return new Mapper<Iterable<?>, Pair<S, T>>() {
            public Pair<S, T> map(Iterable<?> input) {
                return tuple(
                        (S) first(input),
                        (T) first(rest(input)));
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <S, T, V> Mapper<? super Iterable<?>, Triple<S, T, V>> toTriple() {
        return new Mapper<Iterable<?>, Triple<S, T, V>>() {
            public Triple<S, T, V> map(Iterable<?> input) {
                return tuple(
                        (S) first(input),
                        (T) first(rest(input)),
                        (V) first(rest(rest(input))));
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <S, T, U, V> Mapper<? super Iterable<?>, Quadruple<S, T, U, V>> toQuadruple() {
        return new Mapper<Iterable<?>, Quadruple<S, T, U, V>>() {
            public Quadruple<S, T, U, V> map(Iterable<?> iterable) {
                return tuple(
                        (S) first(iterable),
                        (T) first(rest(iterable)),
                        (U) first(rest(rest(iterable))),
                        (V) first(rest(rest(rest(iterable)))));
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
}
