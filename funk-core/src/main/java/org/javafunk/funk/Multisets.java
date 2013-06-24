/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Eagerly.rest;
import static org.javafunk.funk.Literals.collectionFrom;
import static org.javafunk.funk.Literals.iterableWith;

public class Multisets {
    private Multisets() {}

    public static <T> Multiset<T> concatenate(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> concatenatedMultiset = HashMultiset.create(first(iterables).get());
        for (Iterable<? extends T> iterable : rest(iterables)) {
            concatenatedMultiset.addAll(collectionFrom(iterable));
        }
        return concatenatedMultiset;
    }

    public static <T> Multiset<T> union(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> unionMultiset = HashMultiset.create(first(iterables).get());
        for (Iterable<? extends T> iterable : rest(iterables)) {
            Multiset<T> currentMultiset = HashMultiset.create(iterable);
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
        Multiset<T> intersectionMultiset = HashMultiset.create(first(iterables).get());
        for (Iterable<? extends T> iterable : rest(iterables)) {
            intersectionMultiset = com.google.common.collect.Multisets.intersection(
                    intersectionMultiset, HashMultiset.create(iterable));
        }
        return intersectionMultiset;
    }

    public static <T> Multiset<T> difference(Iterable<? extends Iterable<? extends T>> iterables) {
        Multiset<T> differences = HashMultiset.create(first(iterables).get());
        for (Iterable<? extends T> iterable : rest(iterables)) {
            for (T item : iterable) {
                differences.remove(item);
            }
        }
        return differences;
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return concatenate(iterableWith(i1, i2));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return concatenate(iterableWith(i1, i2, i3));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return concatenate(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return concatenate(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return concatenate(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return concatenate(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return concatenate(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return concatenate(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return concatenate(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Multiset<T> concatenate(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return concatenate(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return union(iterableWith(i1, i2));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return union(iterableWith(i1, i2, i3));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return union(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return union(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Multiset<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return intersection(iterableWith(i1, i2));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return intersection(iterableWith(i1, i2, i3));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return intersection(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return intersection(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Multiset<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return difference(iterableWith(i1, i2));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return difference(iterableWith(i1, i2, i3));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return difference(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return difference(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Multiset<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }
}
