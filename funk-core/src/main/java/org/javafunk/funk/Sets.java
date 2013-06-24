/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.javafunk.funk.functors.Predicate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Eagerly.rest;
import static org.javafunk.funk.Iterables.asSet;
import static org.javafunk.funk.Lazily.filter;
import static org.javafunk.funk.Literals.*;

public class Sets {
    private Sets() {}

    public static <T> Set<T> union(Iterable<? extends Iterable<? extends T>> iterables) {
        Set<T> unionSet = new HashSet<T>();
        for (Iterable<? extends T> iterable : iterables) {
            unionSet.addAll(collectionFrom(iterable));
        }
        return unionSet;
    }

    public static <T> Set<T> intersection(Iterable<? extends Iterable<? extends T>> iterables) {
        Set<T> intersectionSet = new HashSet<T>(collectionFrom(first(iterables).get()));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            intersectionSet.retainAll(collectionFrom(iterable));
        }
        return intersectionSet;
    }

    public static <T> Set<T> difference(Iterable<? extends Iterable<? extends T>> iterables) {
        List<Iterable<? extends T>> arguments = listFrom(iterables);
        if (arguments.isEmpty()) {
            return new HashSet<T>();
        } else {
            Set<T> differenceSet = setBuilderFrom(first(arguments).get()).build(HashSet.class);
            differenceSet.removeAll(union(rest(arguments)));
            return differenceSet;
        }
    }

    public static <T> Set<T> symmetricDifference(Iterable<? extends Iterable<? extends T>> iterables) {
        final Multiset<T> unionMultiset = Multisets.concatenate(iterables);
        return asSet(filter(unionMultiset, new Predicate<T>() {
            public boolean evaluate(T element) {
                return isOdd(unionMultiset.count(element));
            }

            private boolean isOdd(Integer value) {
                return value % 2 == 1;
            }
        }));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return union(iterableWith(i1, i2));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return union(iterableWith(i1, i2, i3));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return union(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return union(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return union(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return intersection(iterableWith(i1, i2));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return intersection(iterableWith(i1, i2, i3));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return intersection(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return intersection(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return intersection(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return difference(iterableWith(i1, i2));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return difference(iterableWith(i1, i2, i3));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return difference(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return difference(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return difference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return symmetricDifference(iterableWith(i1, i2));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return symmetricDifference(iterableWith(i1, i2, i3));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4, i5));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4, i5, i6));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4, i5, i6, i7));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    }

    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T> i7, Iterable<? extends T> i8,
            Iterable<? extends T> i9, Iterable<? extends T> i10, Iterable<? extends T>... i11on) {
        return symmetricDifference(iterableWith(i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11on));
    }
}
