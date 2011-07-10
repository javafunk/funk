package org.javafunk;

import org.javafunk.functional.functors.Predicate;
import org.javafunk.functional.functors.Reducer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.javafunk.Iterables.asSet;
import static org.javafunk.Literals.listWith;
import static org.javafunk.functional.Eager.*;
import static org.javafunk.functional.Lazy.filter;

public class Sets {
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

    // TODO: Toby (10/07/11): replace this horrendous implementation with something that uses bags.
    public static <T> Set<T> symmetricDifference(Iterable<? extends Set<? extends T>> sets) {
        Set<T> unionSet = union(sets);
        final HashMap<T, Integer> occurrences = reduce(sets, new HashMap<T, Integer>(),
                new Reducer<Set<? extends T>, HashMap<T, Integer>>() {
                    public HashMap<T, Integer> accumulate(HashMap<T, Integer> accumulator, Set<? extends T> set) {
                        for (T element : set) {
                            Integer currentCount = accumulator.containsKey(element) ? accumulator.get(element) : 0;
                            accumulator.put(element, currentCount + 1);
                        }
                        return accumulator;
                    }
                });
        return asSet(filter(unionSet, new Predicate<T>() {
            public boolean evaluate(T element) {
                return isOdd(occurrences.get(element));
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
