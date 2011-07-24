package org.javafunk.funk;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.javafunk.funk.functors.Predicate;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.javafunk.funk.Eager.first;
import static org.javafunk.funk.Eager.rest;
import static org.javafunk.funk.Iterables.asSet;
import static org.javafunk.funk.Lazy.filter;
import static org.javafunk.funk.Literals.listWith;

public class Sets {
    private Sets() {}

    public static <T> Set<T> union(Iterable<? extends Set<? extends T>> sets) {
        Set<T> unionSet = new HashSet<T>();
        for (Set<? extends T> set : sets) {
            unionSet.addAll(set);
        }
        return unionSet;
    }

    public static <T> Set<T> intersection(Iterable<? extends Set<? extends T>> sets) {
        Set<T> intersectionSet = new HashSet<T>(first(sets));
        for (Set<? extends T> set : rest(sets)) {
            intersectionSet.retainAll(set);
        }
        return intersectionSet;
    }

    public static <T> Set<T> difference(Iterable<? extends Set<? extends T>> sets) {
        Set<T> differenceSet = new HashSet<T>(first(sets));
        for (Set<? extends T> set : rest(sets)) {
            differenceSet.removeAll(set);
        }
        return differenceSet;
    }

    public static <T> Set<T> symmetricDifference(Iterable<? extends Set<? extends T>> sets) {
        final Multiset<T> unionMultiset = HashMultiset.create();
        for (Set<? extends T> set : sets) {
            unionMultiset.addAll(set);
        }
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
            Set<? extends T> s1, Set<? extends T> s2) {
        return union(asList(s1, s2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3) {
        return union(asList(s1, s2, s3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4) {
        return union(asList(s1, s2, s3, s4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5) {
        return union(asList(s1, s2, s3, s4, s5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6) {
        return union(asList(s1, s2, s3, s4, s5, s6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> union(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6, Set<? extends T>... s7on) {
        return union(listWith(s1, s2, s3, s4, s5, s6).and(asList(s7on)));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Set<? extends T> s1, Set<? extends T> s2) {
        return intersection(asList(s1, s2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3) {
        return intersection(asList(s1, s2, s3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4) {
        return intersection(asList(s1, s2, s3, s4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5) {
        return intersection(asList(s1, s2, s3, s4, s5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6) {
        return intersection(asList(s1, s2, s3, s4, s5, s6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> intersection(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6, Set<? extends T>... s7on) {
        return intersection(listWith(s1, s2, s3, s4, s5, s6).and(asList(s7on)));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Set<? extends T> s1, Set<? extends T> s2) {
        return difference(asList(s1, s2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3) {
        return difference(asList(s1, s2, s3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4) {
        return difference(asList(s1, s2, s3, s4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5) {
        return difference(asList(s1, s2, s3, s4, s5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6) {
        return difference(asList(s1, s2, s3, s4, s5, s6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> difference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6, Set<? extends T>... s7on) {
        return difference(listWith(s1, s2, s3, s4, s5, s6).and(asList(s7on)));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Set<? extends T> s1, Set<? extends T> s2) {
        return symmetricDifference(asList(s1, s2));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3) {
        return symmetricDifference(asList(s1, s2, s3));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4) {
        return symmetricDifference(asList(s1, s2, s3, s4));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5) {
        return symmetricDifference(asList(s1, s2, s3, s4, s5));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6) {
        return symmetricDifference(asList(s1, s2, s3, s4, s5, s6));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> symmetricDifference(
            Set<? extends T> s1, Set<? extends T> s2, Set<? extends T> s3, Set<? extends T> s4, Set<? extends T> s5,
            Set<? extends T> s6, Set<? extends T>... s7on) {
        return symmetricDifference(listWith(s1, s2, s3, s4, s5, s6).and(asList(s7on)));
    }
}
