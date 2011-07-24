package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.javafunk.funk.functors.Predicate;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.javafunk.funk.Eager.first;
import static org.javafunk.funk.Eager.rest;
import static org.javafunk.funk.Iterables.asSet;
import static org.javafunk.funk.Lazy.filter;
import static org.javafunk.funk.Literals.listFrom;
import static org.javafunk.funk.Literals.listWith;

public class Sets {
    private Sets() {}

    public static <T> Set<T> union(Iterable<? extends Iterable<? extends T>> iterables) {
        Set<T> unionSet = new HashSet<T>();
        for (Iterable<? extends T> iterable : iterables) {
            unionSet.addAll(listFrom(iterable));
        }
        return unionSet;
    }

    public static <T> Set<T> intersection(Iterable<? extends Iterable<? extends T>> iterables) {
        Set<T> intersectionSet = new HashSet<T>(listFrom(first(iterables)));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            intersectionSet.retainAll(listFrom(iterable));
        }
        return intersectionSet;
    }

    public static <T> Set<T> difference(Iterable<? extends Iterable<? extends T>> iterables) {
        Set<T> differenceSet = new HashSet<T>(listFrom(first(iterables)));
        for (Iterable<? extends T> iterable : rest(iterables)) {
            differenceSet.removeAll(listFrom(iterable));
        }
        return differenceSet;
    }

    public static <T> Set<T> symmetricDifference(Iterable<? extends Iterable<? extends T>> iterables) {
        final Multiset<T> unionMultiset = Multisets.union(iterables);
        return asSet(filter(unionMultiset, new Predicate<T>() {
            public boolean evaluate(T element) {
                return isOdd(unionMultiset.count(element));
            }

            private boolean isOdd(Integer value) {
                return value % 2 == 1;
            }
        }));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return union(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return union(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return union(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return union(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return union(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return union(listWith(i1, i2, i3, i4, i5, i6).and(asList(i7on)));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return intersection(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return intersection(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return intersection(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return intersection(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return intersection(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return intersection(listWith(i1, i2, i3, i4, i5, i6).and(asList(i7on)));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return difference(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return difference(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return difference(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4, 
            Iterable<? extends T> i5) {
        return difference(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4, 
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return difference(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4, 
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return difference(listWith(i1, i2, i3, i4, i5, i6).and(asList(i7on)));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2) {
        return symmetricDifference(asList(i1, i2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3) {
        return symmetricDifference(asList(i1, i2, i3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4) {
        return symmetricDifference(asList(i1, i2, i3, i4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5) {
        return symmetricDifference(asList(i1, i2, i3, i4, i5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6) {
        return symmetricDifference(asList(i1, i2, i3, i4, i5, i6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Iterable<? extends T> i1, Iterable<? extends T> i2, Iterable<? extends T> i3, Iterable<? extends T> i4,
            Iterable<? extends T> i5, Iterable<? extends T> i6, Iterable<? extends T>... i7on) {
        return symmetricDifference(listWith(i1, i2, i3, i4, i5, i6).and(asList(i7on)));
    }
}
