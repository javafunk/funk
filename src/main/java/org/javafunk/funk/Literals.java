/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.javafunk.funk.builders.*;
import org.javafunk.funk.datastructures.tuples.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class Literals {
    private Literals() {}

    public static <E> Iterable<E> iterable() {
        return new IterableBuilder<E>().build();
    }

    public static <E> Iterable<E> iterableOf(Class<E> elementClass) {
        return new IterableBuilder<E>().build();
    }

    public static <E> Iterable<E> iterableFrom(Iterable<? extends E> elements) {
        return new IterableBuilder<E>().with(elements).build();
    }

    public static <E> Iterable<E> iterableFrom(E[] elementArray) {
        return new IterableBuilder<E>().with(elementArray).build();
    }

    public static <E> IterableBuilder<E> iterableBuilder() {
        return new IterableBuilder<E>();
    }

    public static <E> IterableBuilder<E> iterableBuilderOf(Class<E> elementClass) {
        return new IterableBuilder<E>();
    }

    public static <E> IterableBuilder<E> iterableBuilderFrom(Iterable<? extends E> elements) {
        return new IterableBuilder<E>().with(elements);
    }

    public static <E> IterableBuilder<E> iterableBuilderFrom(E[] elementArray) {
        return new IterableBuilder<E>().with(elementArray);
    }

    public static <E> Collection<E> collection() {
        return new CollectionBuilder<E>().build();
    }

    public static <E> Collection<E> collectionOf(Class<E> elementClass) {
        return new CollectionBuilder<E>().build();
    }

    public static <E> Collection<E> collectionFrom(Iterable<? extends E> elements) {
        return new CollectionBuilder<E>().with(elements).build();
    }

    public static <E> Collection<E> collectionFrom(E[] elementArray) {
        return new CollectionBuilder<E>().with(elementArray).build();
    }
    
    public static <E> CollectionBuilder<E> collectionBuilder() {
        return new CollectionBuilder<E>();
    }

    public static <E> CollectionBuilder<E> collectionBuilderOf(Class<E> elementClass) {
        return new CollectionBuilder<E>();
    }

    public static <E> CollectionBuilder<E> collectionBuilderFrom(Iterable<? extends E> elements) {
        return new CollectionBuilder<E>().with(elements);
    }

    public static <E> CollectionBuilder<E> collectionBuilderFrom(E[] elementArray) {
        return new CollectionBuilder<E>().with(elementArray);
    }

    public static <E> List<E> list() {
        return new ListBuilder<E>().build();
    }

    public static <E> List<E> listOf(Class<E> elementClass) {
        return new ListBuilder<E>().build();
    }

    public static <E> List<E> listFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements).build();
    }

    public static <E> List<E> listFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray).build();
    }

    public static <E> ListBuilder<E> listBuilder() {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listBuilderOf(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listBuilderFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements);
    }

    public static <E> ListBuilder<E> listBuilderFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray);
    }

    public static <E> Set<E> set() {
        return new SetBuilder<E>().build();
    }

    public static <E> Set<E> setOf(Class<E> elementClass) {
        return new SetBuilder<E>().build();
    }

    public static <E> Set<E> setFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements).build();
    }

    public static <E> Set<E> setFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray).build();
    }

    public static <E> SetBuilder<E> setBuilder() {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setBuilderOf(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setBuilderFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements);
    }

    public static <E> SetBuilder<E> setBuilderFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray);
    }

    public static <E> Multiset<E> multiset() {
        return new MultisetBuilder<E>().build();
    }

    public static <E> Multiset<E> multisetOf(Class<E> elementClass) {
        return new MultisetBuilder<E>().build();
    }

    public static <E> Multiset<E> multisetFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements).build();
    }

    public static <E> Multiset<E> multisetFrom(E[] elementArray) {
        return new MultisetBuilder<E>().with(elementArray).build();
    }
    
    public static <E> MultisetBuilder<E> multisetBuilder() {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetBuilderOf(Class<E> elementClass) {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetBuilderFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements);
    }

    public static <E> MultisetBuilder<E> multisetBuilderFrom(E[] elementArray) {
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

    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e) { return iterableFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2) { return iterableFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3) { return iterableFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4) { return iterableFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5) { return iterableFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }
    
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e) { return iterableBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2) { return iterableBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3) { return iterableBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4) { return iterableBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e) { return collectionFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2) { return collectionFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3) { return collectionFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4) { return collectionFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5) { return collectionFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e) { return collectionBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2) { return collectionBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3) { return collectionBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4) { return collectionBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }
    
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e) { return listFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2) { return listFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3) { return listFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4) { return listFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5) { return listFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6) { return listFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e) { return listBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2) { return listBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3) { return listBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4) { return listBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5) { return listBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e) { return setFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2) { return setFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3) { return setFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4) { return setFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5) { return setFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6) { return setFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }
    
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e) { return setBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2) { return setBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3) { return setBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4) { return setBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5) { return setBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e) { return multisetFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2) { return multisetFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3) { return multisetFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4) { return multisetFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5) { return multisetFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }
    
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e) { return multisetBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2) { return multisetBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3) { return multisetBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4) { return multisetBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }
}
