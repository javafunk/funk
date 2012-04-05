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

import static java.util.Arrays.asList;
import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Eagerly.rest;
import static org.javafunk.funk.Literals.*;

public class Multisets {
    private Multisets() {}

    public static <T> Multiset<T> concatenate(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> concatenatedMultiset = multisetFrom(first(iterables));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            concatenatedMultiset.addAll(listFrom(iterable));
        }
        return concatenatedMultiset;
    }

    public static <T> Multiset<T> union(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> unionMultiset = multisetFrom(first(iterables));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            Multiset<T> currentMultiset = multisetFrom(iterable);
            for (T element : currentMultiset.elementSet()) {
                int numberInUnionMultiset = unionMultiset.count(element);
                int numberInCurrentMultiset = currentMultiset.count(element);
                if (numberInUnionMultiset < numberInCurrentMultiset) {
                    unionMultiset.setCount(element, numberInCurrentMultiset);
                }
            }
        }
        return unionMultiset;
    }

    public static <T> Multiset<T> intersection(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> intersectionMultiset = multisetFrom(first(iterables));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            intersectionMultiset = com.google.common.collect.Multisets.intersection(
                    intersectionMultiset, multisetFrom(iterable));
        }
        return intersectionMultiset;
    }

    public static <T> Multiset<T> difference(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> differences = multisetFrom(first(iterables));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            for (T item : iterable) {
                differences.remove(item);
            }
        }
        return differences;
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return concatenate(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return concatenate(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return concatenate(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return concatenate(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return concatenate(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return concatenate(listWith(i1, i2, i3, i4, i5, i6).and(i7on));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return union(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return union(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return union(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return union(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return union(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return union(listWith(i1, i2, i3, i4, i5, i6).and(i7on));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return intersection(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return intersection(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return intersection(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return intersection(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return intersection(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return intersection(listWith(i1, i2, i3, i4, i5, i6).and(i7on));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return difference(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return difference(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return difference(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return difference(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return difference(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return difference(listWith(i1, i2, i3, i4, i5, i6).and(i7on));
    }
}
