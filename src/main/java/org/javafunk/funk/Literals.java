/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.builders.*;
import org.javafunk.funk.datastructures.tuples.*;

import static java.util.Arrays.asList;

public class Literals {
    private Literals() {}

    public static <E> IterableBuilder<E> iterable() {
        return new IterableBuilder<E>();
    }

    public static <E> IterableBuilder<E> iterableOf(Class<E> elementClass) {
        return new IterableBuilder<E>();
    }

    public static <E> IterableBuilder<E> iterableFrom(Iterable<? extends E> elements) {
        return new IterableBuilder<E>().with(elements);
    }

    public static <E> IterableBuilder<E> iterableFrom(E[] elementArray) {
        return new IterableBuilder<E>().with(elementArray);
    }
    
    public static <E> CollectionBuilder<E> collection() {
        return new CollectionBuilder<E>();
    }

    public static <E> CollectionBuilder<E> collectionOf(Class<E> elementClass) {
        return new CollectionBuilder<E>();
    }

    public static <E> CollectionBuilder<E> collectionFrom(Iterable<? extends E> elements) {
        return new CollectionBuilder<E>().with(elements);
    }

    public static <E> CollectionBuilder<E> collectionFrom(E[] elementArray) {
        return new CollectionBuilder<E>().with(elementArray);
    }

    public static <E> ListBuilder<E> list() {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listOf(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements);
    }

    public static <E> ListBuilder<E> listFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray);
    }

    public static <E> SetBuilder<E> set() {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setOf(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements);
    }

    public static <E> SetBuilder<E> setFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray);
    }

    public static <E> MultisetBuilder<E> multiset() {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetOf(Class<E> elementClass) {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements);
    }

    public static <E> MultisetBuilder<E> multisetFrom(E[] elementArray) {
        return new MultisetBuilder<E>().with(elementArray);
    }

    public static <K, V> MapBuilder<K, V> map() {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapOf(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapWith(K key, V value) {
        return new MapBuilder<K, V>().with(key, value);
    }

    public static <R> Single<R> tuple(R first) {
        return new Single<R>(first);
    }

    public static <R, S> Pair<R, S> tuple(R first, S second) {
        return new Pair<R, S>(first, second);
    }

    public static <R, S, T> Triple<R, S, T> tuple(R first, S second, T third) {
        return new Triple<R, S, T>(first, second, third);
    }

    public static <R, S, T, U> Quadruple<R, S, T, U> tuple(R first, S second, T third, U fourth) {
        return new Quadruple<R, S, T, U>(first, second, third, fourth);
    }

    public static <R, S, T, U, V> Quintuple<R, S, T, U, V> tuple(R first, S second, T third, U fourth, V fifth) {
        return new Quintuple<R, S, T, U, V>(first, second, third, fourth, fifth);
    }

    public static <R, S, T, U, V, W> Sextuple<R, S, T, U, V, W> tuple(R first, S second, T third, U fourth, V fifth, W sixth) {
        return new Sextuple<R, S, T, U, V, W>(first, second, third, fourth, fifth, sixth);
    }

    public static <R, S, T, U, V, W, X> Septuple<R, S, T, U, V, W, X> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh) {
        return new Septuple<R, S, T, U, V, W, X>(first, second, third, fourth, fifth, sixth, seventh);
    }

    public static <R, S, T, U, V, W, X, Y> Octuple<R, S, T, U, V, W, X, Y> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth) {
        return new Octuple<R, S, T, U, V, W, X, Y>(first, second, third, fourth, fifth, sixth, seventh, eighth);
    }

    public static <R, S, T, U, V, W, X, Y, Z> Nonuple<R, S, T, U, V, W, X, Y, Z> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth, Z ninth) {
        return new Nonuple<R, S, T, U, V, W, X, Y, Z>(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e) {
        return iterableFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2) {
        return iterableFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3) {
        return iterableFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4) {
        return iterableFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4, E e5) {
        return iterableFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> IterableBuilder<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }
    
    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e) {
        return collectionFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2) {
        return collectionFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3) {
        return collectionFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4) {
        return collectionFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4, E e5) {
        return collectionFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return collectionFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> CollectionBuilder<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e) {
        return listFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2) {
        return listFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3) {
        return listFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4) {
        return listFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5) {
        return listFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> ListBuilder<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e) {
        return setFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2) {
        return setFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3) {
        return setFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4) {
        return setFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5) {
        return setFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> SetBuilder<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) {
        return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e) {
        return multisetFrom(asList(e));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2) {
        return multisetFrom(asList(e1, e2));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3) {
        return multisetFrom(asList(e1, e2, e3));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4) {
        return multisetFrom(asList(e1, e2, e3, e4));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5) {
        return multisetFrom(asList(e1, e2, e3, e4, e5));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    @SuppressWarnings("unchecked")
    public static <E> MultisetBuilder<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on));
    }
}
